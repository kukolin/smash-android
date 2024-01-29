package com.anezin.smash.presentation.gameroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anezin.smash.core.domain.Player
import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.LocalDataRepository
import com.anezin.smash.infrastructure.actions.GetRoomFromMemory
import com.anezin.smash.infrastructure.repositories.FirebaseRoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameRoomScreenViewModel(
    private val getRoomFromMemory: GetRoomFromMemory,
    private val localDataRepository: LocalDataRepository,
    private val firebaseRoomRepository: FirebaseRoomRepository
) : ViewModel(
) {
    private val _uiState = MutableStateFlow(GameRoomViewState())
    val uiState: StateFlow<GameRoomViewState> = _uiState.asStateFlow()

    private val _myTurnState = MutableStateFlow(false)
    val myTurnState: StateFlow<Boolean> = _myTurnState.asStateFlow()

    lateinit var room: Room
    fun initializeViewModel() {
        room = getRoomFromMemory()
        val opponents = calculateUiOpponents()
        checkIfMyTurn(room.currentTurn)
        _uiState.value =
            GameRoomViewState().copy(room = room, uiOpponents = opponents)
    }

    private fun checkIfMyTurn(currentTurnId: String) {
        _myTurnState.value = localDataRepository.getMyId() == currentTurnId
    }

    private fun calculateUiOpponents(): List<UIOpponent> {
        val currentTurnId = calculateCurrentTurnId()
        return room.players.filter { it.id != localDataRepository.getMyId() }.map {
            if (it.id == currentTurnId)
                UIOpponent(it, true)
            else UIOpponent(it)
        }.sortedBy { it.opponent.id }
    }

    private fun calculateCurrentTurnId(): String {
        return room.players.first { it.id == room.currentTurn }.id
    }

    private fun calculateNextTurnId(): String {
        val alfOrderPlayers = room.players.sortedBy { it.id }
        val currentPlayer = alfOrderPlayers.first { it.id == room.currentTurn }
        val currentPlayerIndex = alfOrderPlayers.indexOf(currentPlayer)
        var nextPlayerIndex = 0
        if (currentPlayerIndex < alfOrderPlayers.count() - 1) {
            nextPlayerIndex = currentPlayerIndex + 1
        }
        return alfOrderPlayers[nextPlayerIndex].id
    }

    fun drawCard() {
        val nextId = calculateNextTurnId()
        val newRoom = room.copy(currentTurn = nextId)
        localDataRepository.saveLocalRoom(newRoom)
        viewModelScope.launch {
            firebaseRoomRepository.saveRoomData(newRoom)
        }
        calculateUiOpponents()
        checkIfMyTurn(calculateNextTurnId())
        _uiState.value = GameRoomViewState().copy(room = newRoom, uiOpponents = calculateUiOpponents())
    }

    data class GameRoomViewState(val room: Room? = null, val uiOpponents: List<UIOpponent>? = null)

    data class UIOpponent(val opponent: Player, val turnEnabled: Boolean = false)
}
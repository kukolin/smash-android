package com.anezin.smash.presentation.gameroom

import androidx.lifecycle.ViewModel
import com.anezin.smash.core.domain.Player
import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.LocalDataRepository
import com.anezin.smash.infrastructure.actions.GetRoomFromMemory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameRoomScreenViewModel(
    private val getRoomFromMemory: GetRoomFromMemory,
    private val localDataRepository: LocalDataRepository
) : ViewModel(
) {
    private val _uiState = MutableStateFlow(GameRoomViewState())
    val uiState: StateFlow<GameRoomViewState> = _uiState.asStateFlow()

    lateinit var room: Room
    fun initializeViewModel() {
        room = getRoomFromMemory()
        val opponents = calculateOpponents()
        _uiState.value = GameRoomViewState().copy(room = room, opponents = opponents)
    }

    private fun calculateOpponents(): List<Player> {
        val players = room.players
        return players.filter { it.id != localDataRepository.getMyId() }
    }

    private fun calculateCurrentTurnId(): String {
        return room.players.first { it.id == room.currentTurn}.id
    }

    private fun calculateNextTurnId(): String {
        val alfOrderPlayers = room.players.sortedBy { it.id }
        val currentPlayer = alfOrderPlayers.first { it.id == room.currentTurn}
        val currentPlayerIndex = alfOrderPlayers.indexOf(currentPlayer)
        var nextPlayerIndex = 0
        if(alfOrderPlayers.count() != currentPlayerIndex) {
            nextPlayerIndex = currentPlayerIndex + 1
        }
        return alfOrderPlayers[nextPlayerIndex].id
    }

    fun drawCard() {

    }

    data class GameRoomViewState(val room: Room? = null, val opponents: List<Player>? = null)
}
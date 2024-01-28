package com.anezin.smash.presentation.gameroom

import android.util.Log
import androidx.lifecycle.ViewModel
import com.anezin.smash.core.domain.Player
import com.anezin.smash.core.domain.Room
import com.anezin.smash.infrastructure.actions.GetMyIdFromMemory
import com.anezin.smash.infrastructure.actions.GetRoomFromMemory
import com.anezin.smash.presentation.roomscreen.RoomViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameRoomScreenViewModel(
    private val getRoomFromMemory: GetRoomFromMemory,
    private val getMyId: GetMyIdFromMemory
) : ViewModel(
) {
    private val _uiState = MutableStateFlow(GameRoomViewState())
    val uiState: StateFlow<GameRoomViewState> = _uiState.asStateFlow()

    fun initializeViewModel() {
        val opponents = calculateOpponents()
        _uiState.value = GameRoomViewState().copy(room = getRoomFromMemory(), opponents = opponents)
    }

    private fun calculateOpponents(): List<Player>? {
        val players = getRoomFromMemory().players
        return players?.filter { !it.id.equals("id4") }
    }

    data class GameRoomViewState(val room: Room? = null, val opponents: List<Player>? = null)
}
package com.anezin.smash.presentation.gameroom

import androidx.lifecycle.ViewModel
import com.anezin.smash.infrastructure.actions.GetRoomFromMemory
import com.anezin.smash.presentation.roomscreen.RoomViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameRoomScreenViewModel(
    val getRoomFromMemory: GetRoomFromMemory
) : ViewModel(

) {
    private val _uiState = MutableStateFlow(RoomViewState())
    val uiState: StateFlow<RoomViewState> = _uiState.asStateFlow()

    fun initializeViewModel() {
        _uiState.value = RoomViewState().copy(room = getRoomFromMemory())
    }
}
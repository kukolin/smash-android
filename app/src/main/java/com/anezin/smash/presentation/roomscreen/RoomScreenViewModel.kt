package com.anezin.smash.presentation.roomscreen

import androidx.lifecycle.ViewModel
import com.anezin.smash.core.domain.Room
import com.anezin.smash.infrastructure.actions.GetRoomFromMemory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoomScreenViewModel(
    val getRoomFromMemory: GetRoomFromMemory
) : ViewModel(

) {
    private val _uiState = MutableStateFlow(RoomViewState())
    val uiState: StateFlow<RoomViewState> = _uiState.asStateFlow()

    fun initializeViewModel() {
        _uiState.value = RoomViewState().copy(room = getRoomFromMemory())
    }
}

data class RoomViewState(val room: Room? = null)
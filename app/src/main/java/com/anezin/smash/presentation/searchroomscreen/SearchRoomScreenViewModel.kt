package com.anezin.smash.presentation.searchroomscreen

import androidx.lifecycle.ViewModel
import com.anezin.smash.infrastructure.actions.GetRoom
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchRoomScreenViewModel(
    val getRoom: GetRoom
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    suspend fun searchRoom(roomIdText: String) {
        _uiState.value = SearchState().copy(loading = true)
        runCatching {
            getRoom()
        }.getOrNull()
        _uiState.value = SearchState().copy(loading = false)
    }
}

data class SearchState(val loading: Boolean = false)
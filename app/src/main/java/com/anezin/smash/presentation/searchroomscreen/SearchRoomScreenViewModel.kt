package com.anezin.smash.presentation.searchroomscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.anezin.smash.Screen
import com.anezin.smash.core.domain.Room
import com.anezin.smash.infrastructure.actions.GetRoom
import com.anezin.smash.infrastructure.actions.SaveRoomInMemory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchRoomScreenViewModel(
    val getRoom: GetRoom,
    val saveRoomInMemory: SaveRoomInMemory
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    suspend fun searchRoom(roomIdText: String, navController: NavController) {
        _uiState.value = SearchState().copy(loading = true)
        val resultRoomData = getRoom(roomIdText)
        if (resultRoomData.key.isNotBlank()) {
            Log.d("searchRoom", "Creando sala nueva en memoria")
            saveRoomInMemory(resultRoomData)
            navController.navigate(Screen.RoomScreen.route)
        }
        _uiState.value = SearchState().copy(loading = false, room = resultRoomData)
    }
}

data class SearchState(val loading: Boolean = false, val room: Room? = null)
package com.anezin.smash.presentation.searchroomscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.anezin.smash.Screen
import com.anezin.smash.core.domain.Player
import com.anezin.smash.core.domain.Room
import com.anezin.smash.infrastructure.actions.GetRoom
import com.anezin.smash.infrastructure.actions.SaveRoom
import com.anezin.smash.infrastructure.actions.SaveRoomInMemory
import com.anezin.smash.infrastructure.repositories.InMemoryLocalDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchRoomScreenViewModel(
    private val getRoom: GetRoom,
    private val saveRoom: SaveRoom,
    private val saveRoomInMemory: SaveRoomInMemory,
    private val localDataRepository: InMemoryLocalDataRepository
) : ViewModel() {

    private val _feedbackMessageState = MutableStateFlow("")
    val feedbackMessageState: StateFlow<String> = _feedbackMessageState.asStateFlow()

    fun searchRoom(roomIdText: String, navController: NavController) {
        viewModelScope.launch {
            _feedbackMessageState.value = "Cargando..."
            val resultRoomData = getRoom(roomIdText)
            if(resultRoomData.key.isBlank()) {
                _feedbackMessageState.value = "No se encontró la sala."
                return@launch
            }
            if(resultRoomData.players.count() == 4) {
                _feedbackMessageState.value = "Sala llena. Máximo 4 personas."
                return@launch
            }
            addMeToRoom(resultRoomData)
            navController.navigate(Screen.RoomScreen.route)
        }
    }

    private suspend fun addMeToRoom(room: Room) {
        val newPlayers: MutableList<Player> = room.players.toMutableList()
//        newPlayers.add(Player(localDataRepository.getMyId(), localDataRepository.getMyName(), listOf()))
        newPlayers.add(Player(localDataRepository.getMyId(), "asddsa", listOf()))
        val newRoom = room.copy(players = newPlayers)
        saveRoomInMemory(newRoom)
        saveRoom(newRoom)
    }
}
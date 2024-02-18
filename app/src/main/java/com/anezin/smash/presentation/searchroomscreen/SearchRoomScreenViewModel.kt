package com.anezin.smash.presentation.searchroomscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.anezin.smash.Screen
import com.anezin.smash.core.domain.Player
import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.RoomRepository
import com.anezin.smash.infrastructure.actions.GetRoom
import com.anezin.smash.infrastructure.actions.SaveRoom
import com.anezin.smash.infrastructure.repositories.FirebaseRoomRepository
import com.anezin.smash.infrastructure.repositories.InMemoryLocalDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchRoomScreenViewModel(
    private val getRoom: GetRoom,
//    private val saveRoom: SaveRoom,
    private val localDataRepository: InMemoryLocalDataRepository,
    private val roomRepository: FirebaseRoomRepository
) : ViewModel() {

    private val _feedbackMessageState = MutableStateFlow("")
    val feedbackMessageState: StateFlow<String> = _feedbackMessageState.asStateFlow()

    //    init {
//        roomRepository.getRoomData()
//    }
    fun searchRoom(roomIdText: String, navController: NavController) {
        viewModelScope.launch {
            _feedbackMessageState.value = "Cargando..."
            getRoom(roomIdText)
                .let { room ->
                    if (room.key.isBlank()) {
                        _feedbackMessageState.value = "No se encontró la sala."
                        return@launch
                    }
//                    if (room.players.count() == 4) {
//                        _feedbackMessageState.value = "Sala llena. Máximo 4 personas."
//                        return@launch
//                    }
                    addMeToRoom(room)
                    navController.navigate(Screen.RoomScreen.route)
                }
        }
    }

    private suspend fun addMeToRoom(room: Room) {
        val newPlayers: MutableList<Player> = room.players.toMutableList()
//        newPlayers.add(Player(localDataRepository.getMyId(), localDataRepository.getMyName(), listOf()))
        newPlayers.removeIf { it.id == localDataRepository.getMyId() }
        newPlayers.add(
            Player(
                localDataRepository.getMyId(),
                "Lucas",
                mutableListOf(),
                isMe = true,
                turnEnabled = false
            )
        )
        val newRoom = room.copy(players = newPlayers)
        roomRepository.saveRoomData(newRoom)
    }
}
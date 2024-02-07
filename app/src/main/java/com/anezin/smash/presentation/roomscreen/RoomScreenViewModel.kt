package com.anezin.smash.presentation.roomscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.anezin.smash.Screen
import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.RoomRepository
import com.anezin.smash.infrastructure.actions.GetRoomFromMemory
import com.anezin.smash.infrastructure.actions.SaveRoom
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoomScreenViewModel(
    private val getRoomFromMemory: GetRoomFromMemory,
    private val saveRoom: SaveRoom
) : ViewModel(

) {
    private val _uiState = MutableStateFlow(RoomViewState())
    val uiState: StateFlow<RoomViewState> = _uiState.asStateFlow()
    lateinit var room: Room
    fun initializeViewModel() {
        room = getRoomFromMemory()
        _uiState.value = RoomViewState().copy(room = room)
    }

    fun onInitializeGameTaped(navController: NavController) {
        viewModelScope.launch {
            shuffleDeckAndAssign()
            navController.navigate(Screen.GameRoomScreen.route)
        }
    }

    private suspend fun shuffleDeckAndAssign() {
        val cards = generateCards()
        val chunkSize = 60 / room.players.count()
        val cardsToGive = cards.chunked(chunkSize)
        for ((index, player) in room.players.withIndex()) {
            player.cards = cardsToGive[index]
        }
        saveRoom(room)
    }

    private fun generateCards(): List<Int> {
        val cards: MutableList<Int> = mutableListOf()
        for (i in 1..4) {
            for (j in 1..15) {
                cards.add(j)
            }
        }
        return cards.shuffled()
    }
}

data class RoomViewState(val room: Room? = null)
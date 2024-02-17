package com.anezin.smash.presentation.roomscreen

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.anezin.smash.Screen
import com.anezin.smash.core.domain.Room
import com.anezin.smash.infrastructure.repositories.FirebaseRoomRepository
import kotlinx.coroutines.launch

class RoomScreenViewModel(
    private val roomRepository: FirebaseRoomRepository
) : ViewModel() {
    val roomState = roomRepository.roomState

    init {
        roomRepository.subscribeToCardChange("-NnBI5_cAHOVD4X8JTnQ")
    }
    fun onInitializeGameTaped(navController: NavController) {
        viewModelScope.launch {
            shuffleDeckAndAssign()
            navController.navigate(Screen.GameRoomScreen.route)
        }
    }

    private suspend fun shuffleDeckAndAssign() {
        val newRoom: Room = roomState.value!!
        val cards = generateCards()
        val chunkSize = 60 / newRoom.players.count()
        val cardsToGive = cards.chunked(chunkSize)
        for ((index, player) in newRoom.players.withIndex()) {
            player.cards = cardsToGive[index].toMutableList()
        }
        roomRepository.saveRoomData(newRoom)
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
package com.anezin.smash.presentation.gameroom

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anezin.smash.core.domain.Player
import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.LocalDataRepository
import com.anezin.smash.infrastructure.repositories.DummyFirebaseRepository
import com.anezin.smash.infrastructure.repositories.FirebaseRoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameRoomScreenViewModel(
    private val localDataRepository: LocalDataRepository,
    private val firebaseRoomRepository: FirebaseRoomRepository
) : ViewModel(
) {
    val roomState = firebaseRoomRepository.roomState
    init {
        firebaseRoomRepository.subscribeToCardChange("-NnBI5_cAHOVD4X8JTnQ")
    }

    private fun calculateNextTurnId(): String {
        roomState.value?.let {room ->
            val alfOrderPlayers = room.players.sortedBy { it.id }
            val currentPlayer = alfOrderPlayers.first { it.id == room.currentTurn }
            val currentPlayerIndex = alfOrderPlayers.indexOf(currentPlayer)
            var nextPlayerIndex = 0
            if (currentPlayerIndex < alfOrderPlayers.count() - 1) {
                nextPlayerIndex = currentPlayerIndex + 1
            }
            return alfOrderPlayers[nextPlayerIndex].id
        }
        return ""
    }

    fun drawCard() {
        val nextId = calculateNextTurnId()
        viewModelScope.launch {
            roomState.value?.let {
                val room = literallyDrawACard(it)
                firebaseRoomRepository.saveRoomData(room.copy(currentTurn = nextId))
            }
//            literallyDrawACard()
        }
    }

    private fun literallyDrawACard(room: Room):Room {
        var cards = room.players.first { it.id == localDataRepository.getMyId() }.cards
        val card = cards[0]
        cards = cards.drop(1).toMutableList()
        room.players.first { it.id == localDataRepository.getMyId() }.cards = cards
        room.cardStack.add(card)
        return room
    }

    fun getOpponents(room: Room): List<Player> {
        return room.players.filter { it.id != localDataRepository.getMyId() }.map {
            if (it.id == room.currentTurn)
                it.copy(turnEnabled = true)
            else it
        }.sortedBy { it.id }
    }

    fun getMe(room: Room): Player {
        val me = room.players.first { it.id == localDataRepository.getMyId() }
        me.turnEnabled = me.id == room.currentTurn
        return me
    }
}

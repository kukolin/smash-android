package com.anezin.smash.presentation.gameroom

import android.os.CountDownTimer
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anezin.smash.core.domain.Player
import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.domain.SmashTime
import com.anezin.smash.core.interfaces.LocalDataRepository
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

    private val _smashState = MutableStateFlow(false)
    val smashState = _smashState.asStateFlow()

    private val _smashButtonState = MutableStateFlow(false)
    val smashButtonState = _smashButtonState.asStateFlow()

    private val _timerState = MutableStateFlow("")
    val timerState: StateFlow<String> = _timerState.asStateFlow()

    private var _currentTime: Long = 0;

    private val _roomObserver = Observer<Room> { room ->
        checkIfSmash(room)
    }

    private val _smashTimesObserver = Observer<List<SmashTime>> { timeList ->
        checkIfSmashCompleted(timeList.toMutableList())
    }
    init {
        firebaseRoomRepository.subscribeToCardChange("-NnBI5_cAHOVD4X8JTnQ")
        firebaseRoomRepository.subscribeToSmashTimeChange("-NnBI5_cAHOVD4X8JTnQ")
        firebaseRoomRepository.roomState.observeForever(_roomObserver)
        firebaseRoomRepository.smashTimeState.observeForever(_smashTimesObserver)
    }

    private fun checkIfSmashCompleted(timeList: MutableList<SmashTime>) {
        roomState.value?.let { room ->
            if (timeList.isEmpty()) return
            if(room.players.count() <= timeList.count()) {
                timeList.sortBy { it.time }
                room.players.first { it.id == timeList.last().id }.cards.addAll(room.cardStack)
                room.cardStack = emptyList<Int>().toMutableList()
                viewModelScope.launch {
                    firebaseRoomRepository.saveRoomData(room)
                    firebaseRoomRepository.saveSmashTimeData(room.key, emptyList())
                }
            }
        }
    }
    private var timerStarted = false

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

    private fun checkIfSmash(room: Room) {
        if(room.cardStack.count() < 2){
            _smashState.value = false
            return
        }
        val lastCard = room.cardStack.reversed()[0]
        val anteLastCard = room.cardStack.reversed()[1]
        if (lastCard == anteLastCard) {
            startTimer()
        }
        _smashState.value = lastCard == anteLastCard
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
    private val countDownTimer = object : CountDownTimer(10000, 10) {

        override fun onTick(millisUntilFinished: Long) {
            var millis = (1000 - millisUntilFinished % 1000).toString()
            if (millis.length == 2) millis = "0$millis"
            if (millis.length == 1) millis = "00$millis"
            _timerState.value = "${9 - millisUntilFinished / 1000}:$millis"
            _currentTime = 10000 - millisUntilFinished
        }

        override fun onFinish() {
            _timerState.value = "10:000"
            stopTimerAndNotify()
        }
    }
    private fun startTimer() {
        if(timerStarted) return
        timerStarted = true
        _smashButtonState.value = true
        countDownTimer.start()
    }

    fun stopTimerAndNotify() {
        countDownTimer.cancel()
        _smashButtonState.value = false
        timerStarted = false
        viewModelScope.launch {
            roomState.value?.let {room ->
                val smashTimes = firebaseRoomRepository.smashTimeState.value ?: mutableListOf()
                smashTimes.add(SmashTime(localDataRepository.getMyId(), _currentTime))
                firebaseRoomRepository.saveSmashTimeData(room.key, smashTimes.toList())
            }
        }
    }
}

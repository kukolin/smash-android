package com.anezin.smash.presentation.mainscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.anezin.smash.Screen
import com.anezin.smash.core.domain.Player
import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.LocalDataRepository
import com.anezin.smash.core.interfaces.MyIdRepository
import com.anezin.smash.infrastructure.repositories.FirebaseRoomRepository
import kotlinx.coroutines.launch
import java.util.UUID

class MainScreenViewModel(
    private val localDataRepository: LocalDataRepository,
    private val idRepository: MyIdRepository,
    private val firebaseRoomRepository: FirebaseRoomRepository
) : ViewModel() {
    val idState = idRepository.getMyId()
    fun getMyId() {
        viewModelScope.launch {
            idRepository.getMyId().collect {
                Log.d("id", it)
                if (it == "") {
                    val newId = UUID.randomUUID().toString()
                    idRepository.saveMyId(newId)
                    localDataRepository.saveMyId(newId)
                } else {
                    localDataRepository.saveMyId(it)
                }
            }
        }
    }

    fun createNewRoomAndNavigate(navController: NavController) {
        val roomKey = getRandomString(6)
        val newRoom = Room(
            mutableListOf(), "", roomKey, "name", listOf(
                Player(
                    localDataRepository.getMyId(), "name", mutableListOf(),
                    isMe = true,
                    turnEnabled = true
                )
            ), false
        )
        viewModelScope.launch {
            localDataRepository.saveRoomId(newRoom.key)
            firebaseRoomRepository.saveRoomData(newRoom)
            firebaseRoomRepository.subscribeToCardChange(localDataRepository.getRoomId())
            navController.navigate(Screen.RoomScreen.route)
        }
    }

    private fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z')
//        + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}


package com.anezin.smash.infrastructure.actions

import android.util.Log
import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.RoomRepository

class GetRoom(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(roomId: String): Room {
        return try {
            roomRepository.getRoomData(roomId)
        }catch (e: Exception){
            Log.e("RoomRepository error", "No se pudo traer la data del room")
            Room(listOf(), "", "", "", listOf(), false)
        }
    }
}
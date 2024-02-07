package com.anezin.smash.infrastructure.actions

import android.util.Log
import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.RoomRepository

class SaveRoom(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(room: Room) {
        try {
            roomRepository.saveRoomData(room)
        }catch (e: Exception){
            Log.e("RoomRepository error", "No se pudo guardar la data del room")
        }
    }
}
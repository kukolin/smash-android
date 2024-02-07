package com.anezin.smash.core.interfaces

import com.anezin.smash.core.domain.Room
import com.google.firebase.database.ValueEventListener

interface RoomRepository {
    suspend fun getRoomData(roomId: String): Room
    suspend fun saveRoomData(room: Room)
    fun subscribeToCardChange(roomId: String): ValueEventListener
}
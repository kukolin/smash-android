package com.anezin.smash.core.interfaces

import com.anezin.smash.core.domain.Room

interface RoomRepository {
    suspend fun getRoomData(roomId: String): Room
}
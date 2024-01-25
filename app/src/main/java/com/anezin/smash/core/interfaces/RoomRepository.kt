package com.anezin.smash.core.interfaces

import com.anezin.smash.core.domain.RoomData

interface RoomRepository {
    suspend fun getRoomData(): RoomData?
}
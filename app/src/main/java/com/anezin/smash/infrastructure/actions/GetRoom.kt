package com.anezin.smash.infrastructure.actions

import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.RoomRepository

class GetRoom(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(roomId: String): Room? {
        return roomRepository.getRoomData(roomId)
    }
}
package com.anezin.smash.infrastructure.actions

import com.anezin.smash.core.domain.RoomData
import com.anezin.smash.core.interfaces.RoomRepository

class GetRoom(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(): RoomData? {
        return roomRepository.getRoomData()
    }
}
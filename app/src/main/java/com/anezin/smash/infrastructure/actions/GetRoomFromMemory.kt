package com.anezin.smash.infrastructure.actions

import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.LocalDataRepository

class GetRoomFromMemory(
    private val localRoomRepository: LocalDataRepository
){
    operator fun invoke(): Room {
        return localRoomRepository.getLocalRoom()
    }
}
package com.anezin.smash.infrastructure.actions

import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.LocalDataRepository

class GetRoomFromMemory(
    private val localDataRepository: LocalDataRepository
){
    operator fun invoke(): Room {
        return localDataRepository.getLocalRoom()
    }
}
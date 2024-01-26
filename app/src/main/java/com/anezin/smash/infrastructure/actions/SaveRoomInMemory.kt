package com.anezin.smash.infrastructure.actions

import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.LocalDataRepository

class SaveRoomInMemory(
    private val localDataRepository: LocalDataRepository
) {
    operator fun invoke(room: Room) {
        localDataRepository.saveLocalRoom(room)
    }
}
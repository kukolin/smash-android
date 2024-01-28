package com.anezin.smash.infrastructure.actions

import com.anezin.smash.infrastructure.repositories.InMemoryLocalDataRepository
import java.util.UUID

class GetMyIdFromMemory(
    private val inMemoryLocalDataRepository: InMemoryLocalDataRepository
){
    operator fun invoke(): String {
        if(inMemoryLocalDataRepository.getMyId().isEmpty()) {
            var uuid: String = UUID.randomUUID().toString()
            inMemoryLocalDataRepository.saveMyId(uuid)
        }
        return inMemoryLocalDataRepository.getMyId()
    }
}
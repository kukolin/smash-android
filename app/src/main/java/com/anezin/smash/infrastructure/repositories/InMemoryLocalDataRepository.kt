package com.anezin.smash.infrastructure.repositories

import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.LocalDataRepository

class InMemoryLocalDataRepository : LocalDataRepository {
    override fun saveLocalRoom(room: Room) {
        Companion.room = room
    }

    override fun getLocalRoom(): Room {
        return room
    }
    companion object {
        lateinit var room: Room
    }
}
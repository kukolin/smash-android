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

    override fun getMyId(): String {
        return myId
    }

    override fun saveMyId(myId: String) {
        Companion.myId = myId
    }
    companion object {
        lateinit var room: Room
        var myId: String = ""
    }
}
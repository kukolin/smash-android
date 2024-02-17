package com.anezin.smash.core.interfaces

import com.anezin.smash.core.domain.Room

interface LocalDataRepository {
//    fun saveLocalRoom(room: Room)
//    fun getLocalRoom(): Room
    fun getMyId(): String

    fun saveMyId(myId: String)
}
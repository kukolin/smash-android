package com.anezin.smash.core.interfaces

interface LocalDataRepository {
    fun getMyId(): String
    fun saveMyId(myId: String)
    fun getRoomId(): String
    fun saveRoomId(roomId: String)
}
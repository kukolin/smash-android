package com.anezin.smash.infrastructure.repositories

import com.anezin.smash.core.domain.Player
import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.interfaces.RoomRepository
import kotlinx.coroutines.delay

class DummyFirebaseRepository : RoomRepository {
    override suspend fun getRoomData(roomId: String): Room? {
        delay(1000)
        return dummyRoom
    }
    companion object {
        private val dummyPlayer1 = Player("id1", "name1", 0, listOf())
        private val dummyPlayer2 = Player("id2", "name2", 0, listOf())
        private val dummyPlayer3 = Player("id3", "name3", 0, listOf())
        private val dummyPlayers = listOf(dummyPlayer1, dummyPlayer2, dummyPlayer3)
        private val dummyRoom = Room(listOf(), 0, "-NnBI5_cAHOVD4X8JTnQ", "roomName", dummyPlayers, true)
    }
}
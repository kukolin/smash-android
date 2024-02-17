package com.anezin.smash.infrastructure.repositories

import com.anezin.smash.core.interfaces.LocalDataRepository

class InMemoryLocalDataRepository : LocalDataRepository {

    override fun getMyId(): String {
        return myId
    }

    override fun saveMyId(myId: String) {
        Companion.myId = myId
    }
    companion object {
        var myId: String = ""
    }
}
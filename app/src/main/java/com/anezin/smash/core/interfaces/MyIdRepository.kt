package com.anezin.smash.core.interfaces

import kotlinx.coroutines.flow.Flow

interface MyIdRepository {
    fun getMyId(): Flow<String>

    suspend fun saveMyId(myId: String)
}
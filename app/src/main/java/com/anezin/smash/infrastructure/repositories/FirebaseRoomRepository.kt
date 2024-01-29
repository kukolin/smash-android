package com.anezin.smash.infrastructure.repositories

import android.util.Log
import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.domain.RoomResponse
import com.anezin.smash.core.interfaces.RoomRepository
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseRoomRepository(
    private val database: FirebaseDatabase = Firebase.database
) : RoomRepository {
    override suspend fun getRoomData(roomId: String): Room {
        return suspendCancellableCoroutine { continuation ->

            val myRef = database.getReference("rooms/$roomId")

            myRef.get().addOnSuccessListener { snapshot ->
                val roomResponse = snapshot.getValue<RoomResponse>() ?: throw Exception()
                continuation.resume(roomResponse.toRoom())
            }.addOnFailureListener { exception ->
                Log.e("firebase", "Error getting data", exception)
                continuation.resumeWithException(exception)
            }
        }
    }

    override suspend fun saveRoomData(room: Room) {

    }
}

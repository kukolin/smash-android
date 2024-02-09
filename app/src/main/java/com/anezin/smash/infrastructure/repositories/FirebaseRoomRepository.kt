package com.anezin.smash.infrastructure.repositories

import android.util.Log
import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.domain.RoomResponse
import com.anezin.smash.core.interfaces.RoomRepository
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseRoomRepository(
    private val database: FirebaseDatabase = Firebase.database
) : RoomRepository {
    private val _roomState = MutableStateFlow(Room(mutableListOf(), "","","", listOf(), false))
    val roomState: StateFlow<Room> = _roomState.asStateFlow()
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

    override fun subscribeToCardChange(roomId: String): ValueEventListener {
        val myRef = database.getReference("rooms/$roomId")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val roomResponse = dataSnapshot.getValue<RoomResponse>()
                if (roomResponse != null) {
                    _roomState.value = roomResponse.toRoom()
                } else {
                    Log.e("FirebaseRoomRepository", "Room data is null")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("FirebaseRoomRepository", "loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.addValueEventListener(postListener)
        return postListener
    }

    override suspend fun saveRoomData(room: Room) {
        val myRef = database.getReference("rooms/${room.key}")
        myRef.setValue(room)
    }
}

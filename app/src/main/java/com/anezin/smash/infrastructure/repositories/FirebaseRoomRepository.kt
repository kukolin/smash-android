package com.anezin.smash.infrastructure.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.anezin.smash.core.domain.Room
import com.anezin.smash.core.domain.RoomResponse
import com.anezin.smash.core.domain.SmashTime
import com.anezin.smash.core.domain.SmashTimeResponse
import com.anezin.smash.core.interfaces.RoomRepository
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseRoomRepository(
    private val database: FirebaseDatabase = Firebase.database
) : RoomRepository {
    val roomState = MutableLiveData(Room())
    val smashTimeState = MutableLiveData(mutableListOf<SmashTime>())
    override suspend fun getRoomData(roomId: String): Room {
        if(roomId.isBlank()) return Room()
        return suspendCancellableCoroutine { continuation ->
            val myRef = database.getReference("rooms/$roomId/room")
            Log.d("pidiendo data", roomId)
            myRef.get().addOnSuccessListener { snapshot ->
                Log.d("entro", "entro bien")
                val roomResponse = snapshot.getValue<RoomResponse>() ?: throw Exception()
                roomState.value = roomResponse.toRoom()
                continuation.resume(roomResponse.toRoom())
            }.addOnFailureListener { exception ->
                Log.e("firebase", "Error", exception)
                continuation.resumeWithException(exception)
            }
        }
    }

    override fun subscribeToCardChange(roomId: String): ValueEventListener {
        val myRef = database.getReference("rooms/$roomId/room")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val roomResponse = dataSnapshot.getValue<RoomResponse>()
                if (roomResponse != null) {
                    Log.d("FirebaseChanged", roomResponse.toString());
                    roomState.value = roomResponse.toRoom()
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

    override fun subscribeToSmashTimeChange(roomId: String): ValueEventListener {
        val myRef = database.getReference("rooms/$roomId/smashTime")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val smashResponse = dataSnapshot.getValue<MutableList<SmashTimeResponse>>()
                if (smashResponse != null) {
                    Log.d("FirebaseChanged", smashResponse.toString());
                    smashTimeState.value = smashResponse.map { it.toSmashTime() }.toMutableList()
                } else {
                    smashTimeState.value = mutableListOf()
                    Log.e("FirebaseRoomRepository", "smashTime data is null")
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
        val myRef = database.getReference("rooms/${room.key}/room")
        myRef.setValue(room)
    }

    override suspend fun saveSmashTimeData(roomKey: String, smashTime: List<SmashTime>) {
        val myRef = database.getReference("rooms/$roomKey/smashTime")
        myRef.setValue(smashTime)
    }
}

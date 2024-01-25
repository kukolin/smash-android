package com.anezin.smash.infrastructure.repositories

import android.util.Log
import com.anezin.smash.core.domain.RoomData
import com.anezin.smash.core.interfaces.RoomRepository
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class FirebaseRoomRepository(
    private val database: FirebaseDatabase = Firebase.database
) : RoomRepository {
    override suspend fun getRoomData(): RoomData? {
        val myRef = database.getReference("room")
        var roomData: RoomData? = null;
        myRef.child("").get().addOnSuccessListener {
            roomData = it.getValue<RoomData>()
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return roomData
    }
}

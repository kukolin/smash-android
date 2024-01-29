package com.anezin.smash.infrastructure.factories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.anezin.smash.infrastructure.actions.GetRoom
import com.anezin.smash.infrastructure.actions.GetRoomFromMemory
import com.anezin.smash.infrastructure.actions.SaveRoomInMemory
import com.anezin.smash.infrastructure.repositories.FirebaseRoomRepository
import com.anezin.smash.infrastructure.repositories.InMemoryLocalDataRepository
import com.anezin.smash.infrastructure.repositories.SharedPrefsIdRepository
import com.anezin.smash.presentation.gameroom.GameRoomScreenViewModel
import com.anezin.smash.presentation.mainscreen.MainScreenViewModel
import com.anezin.smash.presentation.roomscreen.RoomScreenViewModel
import com.anezin.smash.presentation.searchroomscreen.SearchRoomScreenViewModel

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class Factory {
    companion object {
        private val firebaseRepository = FirebaseRoomRepository()
//        private val firebaseRepository = DummyFirebaseRepository()
        private val localDataRepository = InMemoryLocalDataRepository()

        private val getRoomAction = GetRoom(firebaseRepository)
        private val saveRoomInMemory = SaveRoomInMemory(localDataRepository)
        private val getRoomFromMemory = GetRoomFromMemory(localDataRepository)

        val searchRoomScreenViewModel =
            SearchRoomScreenViewModel(getRoomAction, saveRoomInMemory)
        val roomScreenViewModel = RoomScreenViewModel(getRoomFromMemory)
        val gameRoomScreenViewModel = GameRoomScreenViewModel(getRoomFromMemory, localDataRepository, firebaseRepository)
        private fun sharedPrefsIdRepository(context: Context) =
            SharedPrefsIdRepository(context.dataStore)
        fun mainScreenViewModel(context: Context) = MainScreenViewModel(localDataRepository, sharedPrefsIdRepository(context))
    }
}
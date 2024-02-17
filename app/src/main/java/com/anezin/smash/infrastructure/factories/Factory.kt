package com.anezin.smash.infrastructure.factories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.anezin.smash.infrastructure.actions.GetRoom
import com.anezin.smash.infrastructure.actions.SaveRoom
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
        private val saveRoomAction = SaveRoom(firebaseRepository)

        val searchRoomScreenViewModel =
            SearchRoomScreenViewModel(getRoomAction, localDataRepository, firebaseRepository)
        val roomScreenViewModel = RoomScreenViewModel(firebaseRepository)
        val gameRoomScreenViewModel = GameRoomScreenViewModel(localDataRepository, firebaseRepository)
        private fun sharedPrefsIdRepository(context: Context) =
            SharedPrefsIdRepository(context.dataStore)
        fun mainScreenViewModel(context: Context) = MainScreenViewModel(localDataRepository, sharedPrefsIdRepository(context))
    }
}
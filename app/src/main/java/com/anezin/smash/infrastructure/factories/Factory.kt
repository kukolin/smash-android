package com.anezin.smash.infrastructure.factories

import com.anezin.smash.infrastructure.actions.GetRoom
import com.anezin.smash.infrastructure.actions.GetRoomFromMemory
import com.anezin.smash.infrastructure.actions.SaveRoomInMemory
import com.anezin.smash.infrastructure.repositories.FirebaseRoomRepository
import com.anezin.smash.infrastructure.repositories.InMemoryLocalDataRepository
import com.anezin.smash.presentation.roomscreen.RoomScreenViewModel
import com.anezin.smash.presentation.searchroomscreen.SearchRoomScreenViewModel

class Factory {
    companion object {
        private val firebaseRepository = FirebaseRoomRepository()

        private val localDataRepository = InMemoryLocalDataRepository()

        private val getRoomAction = GetRoom(firebaseRepository)

        private val saveRoomInMemory = SaveRoomInMemory(localDataRepository)

        private val getRoomFromMemory = GetRoomFromMemory(localDataRepository)

        val searchRoomScreenViewModel =
            SearchRoomScreenViewModel(getRoomAction, saveRoomInMemory)

        val roomScreenViewModel: RoomScreenViewModel = RoomScreenViewModel(getRoomFromMemory)
    }
}
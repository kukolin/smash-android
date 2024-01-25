package com.anezin.smash.infrastructure.factories

import com.anezin.smash.infrastructure.actions.GetRoom
import com.anezin.smash.infrastructure.repositories.FirebaseRoomRepository
import com.anezin.smash.presentation.searchroomscreen.SearchRoomScreenViewModel

class Factory {
    companion object {
        private val firebaseRepository = FirebaseRoomRepository()

        private val getRoomAction = GetRoom(firebaseRepository)

        val searchRoomScreenViewModel: SearchRoomScreenViewModel =
            SearchRoomScreenViewModel(getRoomAction)
    }
}
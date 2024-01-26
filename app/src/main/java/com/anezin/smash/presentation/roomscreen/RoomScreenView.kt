package com.anezin.smash.presentation.roomscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.anezin.smash.infrastructure.factories.Factory

class RoomScreenView {

    @Composable
    fun Build(navController: NavController, viewModel: RoomScreenViewModel = Factory.roomScreenViewModel) {
        viewModel.initializeViewModel()
        val foundRoomState by viewModel.uiState.collectAsState()

        Column {
            Text("Cantidad de gente en la sala: ${foundRoomState.room?.players?.count()}")

        }
    }
}
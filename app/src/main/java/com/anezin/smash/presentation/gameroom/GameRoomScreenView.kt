package com.anezin.smash.presentation.gameroom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.anezin.smash.infrastructure.factories.Factory

class GameRoomScreenView {
    @Composable
    fun Build(
        navController: NavController,
        viewModel: GameRoomScreenViewModel = Factory.gameRoomScreenViewModel
    ) {
        viewModel.initializeViewModel()
        val state by viewModel.uiState.collectAsState()

        Content()
    }

    @Composable
    private fun Content() {

    }
}
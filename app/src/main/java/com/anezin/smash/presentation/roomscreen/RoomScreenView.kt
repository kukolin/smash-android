package com.anezin.smash.presentation.roomscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.anezin.smash.infrastructure.factories.Factory
import kotlinx.coroutines.launch

class RoomScreenView {

    @Preview(showSystemUi = true)
    @Composable
    fun Preview() {
        Content(playerCount = 1)
    }

    @Composable
    fun Build(
        navController: NavController,
        viewModel: RoomScreenViewModel = Factory.roomScreenViewModel
    ) {
        viewModel.initializeViewModel()
        val foundRoomState by viewModel.uiState.collectAsState()

        Content(foundRoomState.room?.players?.count())
    }

    @Composable
    private fun Content(playerCount: Int?) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Cantidad de gente en la sala: ${playerCount}/4")
            Button(onClick = {
            }) {
                Text(text = "Empezar partida")
            }
        }
    }
}
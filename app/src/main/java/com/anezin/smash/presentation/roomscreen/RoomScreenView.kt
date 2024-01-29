package com.anezin.smash.presentation.roomscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.anezin.smash.Screen
import com.anezin.smash.core.domain.Player
import com.anezin.smash.infrastructure.factories.Factory

class RoomScreenView {

    @Preview(showSystemUi = true)
    @Composable
    fun Preview() {
        Content(rememberNavController(), dummyPlayers)
    }

    @Composable
    fun Build(
        navController: NavController,
        viewModel: RoomScreenViewModel = Factory.roomScreenViewModel
    ) {
        viewModel.initializeViewModel()
        val foundRoomState by viewModel.uiState.collectAsState()

        Content(navController, foundRoomState.room?.players)
    }

    @Composable
    private fun Content(
        navController: NavController,
        players: List<Player>?
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Cantidad de gente en la sala: ${players?.count()}/4")
                if (players != null) {
                    var index = 1
                    for (p in players) {
                        Text("$index. ${players[index - 1].name}")
                        index++
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    navController.navigate(Screen.GameRoomScreen.route)
                }) {
                    Text(text = "Empezar partida")
                }
            }
        }
    }

    companion object {
        val dummyPlayers =
            listOf(Player("id", "name1", listOf()), Player("id", "name2", listOf()))
    }
}
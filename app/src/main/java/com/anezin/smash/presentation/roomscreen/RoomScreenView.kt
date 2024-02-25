package com.anezin.smash.presentation.roomscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.anezin.smash.core.domain.Player
import com.anezin.smash.core.domain.Room
import com.anezin.smash.infrastructure.factories.Factory

class RoomScreenView {

    private lateinit var viewModel: RoomScreenViewModel

    @Preview(showSystemUi = true)
    @Composable
    fun Preview() {
        Content(rememberNavController(), Room())
    }

    @Composable
    fun Build(
        navController: NavController,
        viewModel: RoomScreenViewModel = Factory.roomScreenViewModel
    ) {
        this.viewModel = viewModel
        val foundRoomState by this.viewModel.roomState.observeAsState()

        foundRoomState?.let { Content(navController, it) }
    }

    @Composable
    private fun Content(
        navController: NavController,
        room: Room
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
                Text("Cantidad de gente en la sala: ${room.players.count()}/4", fontSize = 20.sp)
                var index = 1
                for (p in room.players) {
                    Text("$index. ${room.players[index - 1].name}", fontSize = 25.sp)
                    index++
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(modifier = Modifier.weight(20f).wrapContentHeight(), onClick = {
                    viewModel.onInitializeGameTaped(navController)
                }) {
                    Text(text = "Empezar partida", fontSize = 22.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.weight(20f),
                    text = "Código de sala: ${room.key}",
                    fontSize = 25.sp
                )
            }
        }
    }

    companion object {
        val dummyPlayers =
            listOf(
                Player("id", "name1", mutableListOf(), false, false),
                Player("id", "name2", mutableListOf(), false, false)
            )
    }
}
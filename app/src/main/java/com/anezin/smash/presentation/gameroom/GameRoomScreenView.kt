package com.anezin.smash.presentation.gameroom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.anezin.smash.R
import com.anezin.smash.core.domain.Player
import com.anezin.smash.core.domain.Room
import com.anezin.smash.infrastructure.factories.Factory

class GameRoomScreenView {
    @Composable
    fun Build(
        navController: NavController,
        viewModel: GameRoomScreenViewModel = Factory.gameRoomScreenViewModel
    ) {
        viewModel.initializeViewModel()
        val state by viewModel.uiState.collectAsState()

        Content(state.room)
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun Preview() {
        Content(dummyRoom)
    }

    @Composable
    private fun Content(room: Room?) {
        if(room == null) return
        if(room.players == null) return

        Column {
            Row(modifier = Modifier
                .weight(2f)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
//hacer una lista de opponents en el viewmodel e iterarlo aca
                PlayerCell(room.players[0])
                PlayerCell(room.players[1])
                PlayerCell(room.players[2])
            }
            Box(modifier = Modifier.weight(13f)) {

            }
        }
    }

    @Composable
    private fun PlayerCell(player: Player) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(10.dp))
            player.name?.let { Text(it) }
            Image(ImageVector.vectorResource(R.drawable.avatar), contentDescription = "avatar")
        }
    }

    companion object {
        private val dummyPlayer = Player("id", "name1", 0, listOf())
        private val dummyPlayers = listOf(dummyPlayer, dummyPlayer, dummyPlayer, dummyPlayer)
        private val dummyRoom = Room(listOf(), 0, "-NnBI5_cAHOVD4X8JTnQ", "roomName", dummyPlayers, true)
    }
}
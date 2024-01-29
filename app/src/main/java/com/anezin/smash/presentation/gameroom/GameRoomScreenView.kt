package com.anezin.smash.presentation.gameroom

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.anezin.smash.R
import com.anezin.smash.core.domain.Player
import com.anezin.smash.core.domain.Room
import com.anezin.smash.infrastructure.factories.Factory

class GameRoomScreenView {
    private lateinit var viewModel: GameRoomScreenViewModel

    @Composable
    fun Build(
        navController: NavController,
        viewModel: GameRoomScreenViewModel = Factory.gameRoomScreenViewModel
    ) {
        this.viewModel = viewModel
        viewModel.initializeViewModel()
        val state by viewModel.uiState.collectAsState()

        Content(state.room, state.opponents)
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun Preview() {
        Content(dummyRoom, dummyRoom.players)
    }

    @Composable
    private fun Content(room: Room?, opponents: List<Player>?) {
        if (room == null) return
//        if (opponents == null) return

        Column {
            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Log.d("op", "asd" + room.players.toString())
//                for (op in opponents) {
//                    PlayerCell(op)
//                }
            }
            Spacer(modifier = Modifier.weight(5f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(8f)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(Modifier.weight(0.4f))
                CardsImage(Modifier.weight(3f))
                Spacer(Modifier.weight(1f))
                DrawCardButton(Modifier.weight(3f))
                Spacer(Modifier.weight(0.4f))
            }
        }
    }

    @Composable
    private fun CardsImage(modifier: Modifier) {
        Image(
            ImageVector.vectorResource(R.drawable.deck),
            contentDescription = "deck",
            modifier = modifier
        )
    }

    @Composable
    private fun DrawCardButton(modifier: Modifier) {
        Button(
            modifier = modifier
                .aspectRatio(1f),
            border = BorderStroke(1.dp, Color.Magenta),
            onClick = {
                viewModel.drawCard()
            },
        ) {
            Text(text = "Draw card")
        }
    }

    @Composable
    private fun PlayerCell(player: Player) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            player.name?.let { Text(it) }
            Image(ImageVector.vectorResource(R.drawable.avatar), contentDescription = "avatar")
        }
    }

    companion object {
        private val dummyPlayer = Player("id", "name1", listOf())
        private val dummyPlayers = listOf(dummyPlayer, dummyPlayer, dummyPlayer)
        private val dummyRoom =
            Room(listOf(), "id4", "-NnBI5_cAHOVD4X8JTnQ", "roomName", dummyPlayers, true)
    }
}
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        val isMyTurn by viewModel.myTurnState.collectAsState()
        val room by viewModel.roomState.collectAsState()

        Content(room, state.uiOpponents, isMyTurn)
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun Preview() {
        Content(dummyRoom, dummyOpponents, true)
    }

    @Composable
    private fun Content(
        room: Room?,
        uiOpponents: List<GameRoomScreenViewModel.UIOpponent>?,
        isMyTurn: Boolean
    ) {
        if (room == null) return
        if (uiOpponents == null) return

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .weight(5f)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (op in uiOpponents) {
                    PlayerCell(op)
                }
            }
            Spacer(modifier = Modifier.weight(3f))
            Text("0", fontSize = 50.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(3f))
            Text("Tour turn!", fontSize = 40.sp, modifier = Modifier.alpha(if(isMyTurn) 1f else 0f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(6f)
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
    private fun PlayerCell(opponent: GameRoomScreenViewModel.UIOpponent) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(2f))
            Text(opponent.opponent.name, modifier = Modifier.weight(1f))
            Image(
                ImageVector.vectorResource(R.drawable.avatar),
                contentDescription = "avatar",
                modifier = Modifier.weight(3f)
            )
            Text("cards: " + opponent.opponent.cards.count(), modifier = Modifier.weight(1f))
            Image(
                ImageVector.vectorResource(R.drawable.up_arrow),
                contentDescription = "arrow",
                modifier = Modifier
                    .weight(1f)
                    .alpha(if (opponent.turnEnabled) 1f else 0f)
            )
        }
    }

    companion object {
        private val dummyPlayer = Player("id", "name1", listOf(0,2))
        private val dummyOpponents = listOf(dummyPlayer, dummyPlayer, dummyPlayer).map { GameRoomScreenViewModel.UIOpponent(it,true) }
            private val dummyPlayers = listOf(dummyPlayer, dummyPlayer, dummyPlayer)
        private val dummyRoom =
            Room(listOf(), "id4", "-NnBI5_cAHOVD4X8JTnQ", "roomName", dummyPlayers, true)
    }
}
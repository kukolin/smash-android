package com.anezin.smash.presentation.gameroom

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
import androidx.compose.runtime.livedata.observeAsState

class GameRoomScreenView {
    private lateinit var viewModel: GameRoomScreenViewModel

    @Composable
    fun Build(
        navController: NavController,
        viewModel: GameRoomScreenViewModel = Factory.gameRoomScreenViewModel
    ) {
        this.viewModel = viewModel
        val room by viewModel.roomState.observeAsState()
        room?.let {
            val opponents = viewModel.getOpponents(it)
            val me = viewModel.getMe(it)
            Content(it, opponents, me)
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun Preview() {
        Content(dummyRoom, dummyPlayers, dummyPlayer)
    }

    @Composable
    private fun Content(
        room: Room,
        uiOpponents: List<Player>,
        me: Player
    ) {
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
            Text(getLastCardFromStack(room), fontSize = 50.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(3f))
            Text("Tour turn!", fontSize = 40.sp, modifier = Modifier.alpha(if(me.turnEnabled) 1f else 0f))
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
            Text("Tus cartas: ${viewModel.getMe(room).cards.count()}", fontSize = 30.sp)
            Spacer(Modifier.weight(1f))
        }
    }

    fun getLastCardFromStack(room: Room): String{
        return room.cardStack.last().toString()
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
    private fun PlayerCell(opponent: Player) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(2f))
            Text(opponent.name, modifier = Modifier.weight(1f))
            Image(
                ImageVector.vectorResource(R.drawable.avatar),
                contentDescription = "avatar",
                modifier = Modifier.weight(3f)
            )
            Text("cards: " + opponent.cards.count(), modifier = Modifier.weight(1f))
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
        private val dummyPlayer = Player("id", "name1", mutableListOf(0,2), false, false)
        private val dummyPlayerMe = Player("id", "name1", mutableListOf(0,2, 3, 4), true, false)
        private val dummyPlayers = listOf(dummyPlayer, dummyPlayer, dummyPlayerMe)
        private val dummyRoom =
            Room(mutableListOf(1, 2, 3), "id4", "-NnBI5_cAHOVD4X8JTnQ", "roomName", dummyPlayers, true)
    }
}
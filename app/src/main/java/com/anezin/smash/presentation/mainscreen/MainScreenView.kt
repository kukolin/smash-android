package com.anezin.smash.presentation.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.anezin.smash.Screen
import com.anezin.smash.infrastructure.factories.Factory

class MainScreenView {

    @Composable
    fun Build(
        navController: NavController,
        viewModel: MainScreenViewModel = Factory.mainScreenViewModel(LocalContext.current)
    ) {
        val myId by viewModel.idState.collectAsState(initial = "")
        viewModel.getMyId()

        Column {
            Row(
                horizontalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(text = "Smash!", fontSize = 40.sp)
            }
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                if (myId.isNotBlank()) Button(onClick = {
                    navController.navigate(Screen.SearchRoomScreen.route)
                }) {
                    Text(text = "Join room")
                }
            }
        }

    }
}
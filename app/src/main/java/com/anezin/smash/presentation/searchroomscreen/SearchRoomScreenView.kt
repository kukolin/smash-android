package com.anezin.smash.presentation.searchroomscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.anezin.smash.infrastructure.factories.Factory

class SearchRoomScreenView {

    @Preview(showSystemUi = true)
    @Composable
    fun Preview() {
        Text("asd")
    }

    @Composable
    fun Build(navController: NavController, viewModel: SearchRoomScreenViewModel = Factory.searchRoomScreenViewModel) {
        var text by remember {
            mutableStateOf("")
        }
        val feedbackMessage by viewModel.feedbackMessageState.collectAsState()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Ingrese el ID de la sala:")
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = text, onValueChange = { text = it })
            Spacer(modifier = Modifier.height(8.dp))
            if(feedbackMessage.isNotEmpty()) Text(text = feedbackMessage)

            Button(onClick = {
                viewModel.searchRoom(text, navController)
            }) {
                Text(text = "Go")
            }
        }
    }
}
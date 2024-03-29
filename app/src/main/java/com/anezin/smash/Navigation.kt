package com.anezin.smash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anezin.smash.presentation.gameroom.GameRoomScreenView
import com.anezin.smash.presentation.mainscreen.MainScreenView
import com.anezin.smash.presentation.roomscreen.RoomScreenView
import com.anezin.smash.presentation.searchroomscreen.SearchRoomScreenView

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreenView().Build(navController = navController)
        }
        composable(route = Screen.RoomScreen.route) {
            RoomScreenView().Build(navController = navController)
        }
        composable(route = Screen.SearchRoomScreen.route) {
            SearchRoomScreenView().Build(navController = navController)
        }
        composable(route = Screen.GameRoomScreen.route) {
            GameRoomScreenView().Build(navController = navController)
        }
        composable(
            route = Screen.DetailScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "Lucas"
                }
            )
        ) {entry ->
            DetailScreen(name = entry.arguments?.getString("name"))  //Example
        }
    }
}

@Composable //Example
fun DetailScreen(name: String?) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "Hello, $name")
    }
}
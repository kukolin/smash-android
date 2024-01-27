package com.anezin.smash

sealed class Screen(val route: String) {
    data object MainScreen : Screen("main_screen")
    data object DetailScreen : Screen("detail_screen")
    data object SearchRoomScreen : Screen("search_room_screen")
    data object RoomScreen : Screen("room_screen")
    data object GameRoomScreen : Screen("game_room_screen")

    fun withArgs(vararg args: String):String {
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
}
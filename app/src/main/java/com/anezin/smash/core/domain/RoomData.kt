package com.anezin.smash.core.domain

data class RoomData(
    val cardStack: List<Int>,
    val currentTurn: Int,
    val key: String,
    val name: String,
    val players: Player,
    val started: Boolean
)

data class Player(val id: String, val name: String, val turnNumber: Int, val cards: List<Int>)
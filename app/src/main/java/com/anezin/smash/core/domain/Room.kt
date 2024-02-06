package com.anezin.smash.core.domain

data class Room(
    val cardStack: List<Int>,
    val currentTurn: String,
    val key: String,
    val name: String,
    val players: List<Player>,
    val started: Boolean
)

data class Player(val id: String, val name: String, var cards: List<Int>)
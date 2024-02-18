package com.anezin.smash.core.domain

data class Room(
    var cardStack: MutableList<Int>,
    val currentTurn: String,
    val key: String,
    val name: String,
    val players: List<Player>,
    val started: Boolean,
)

data class Player(
    val id: String,
    val name: String,
    var cards: MutableList<Int>,
    val isMe: Boolean,
    var turnEnabled: Boolean
)
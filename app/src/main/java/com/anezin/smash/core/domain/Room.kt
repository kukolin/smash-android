package com.anezin.smash.core.domain

data class Room constructor(
    val cardStack: List<Int>? = null,
    val currentTurn: String? = null,
    val key: String? = null,
    val name: String? = null,
    val players: List<Player>? = null,
    val started: Boolean? = null
)

data class Player(val id: String? = null, val name: String? = null, val cards: List<Int>? = null)
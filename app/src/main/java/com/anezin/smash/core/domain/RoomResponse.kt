package com.anezin.smash.core.domain

import com.google.gson.annotations.SerializedName

data class RoomResponse(
    @SerializedName("cardStack") val cardStack: MutableList<Int> = mutableListOf(),
    @SerializedName("currentTurn") val currentTurn: String = "",
    @SerializedName("key") val key: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("players") val players: List<PlayerResponse> = listOf(),
    @SerializedName("started") val started: Boolean = false,
    @SerializedName("smashTimes") val smashTimes: MutableList<SmashTimeResponse> = mutableListOf(),

) {
    fun toRoom(): Room {
        return Room(
            cardStack,
            currentTurn,
            key,
            name,
            players.map { it.toPlayer() },
            started
        )
    }
}

data class PlayerResponse(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("cards") val cards: MutableList<Int> = mutableListOf()
) {

    fun toPlayer(): Player {
        return Player(id, name, cards, isMe = false, turnEnabled = false)
    }
}

data class SmashTimeResponse(
    @SerializedName("id") val id: String = "",
    @SerializedName("time") val time: Long = 0,
) {

    fun toSmashTime(): SmashTime {
        return SmashTime(id, time)
    }
}
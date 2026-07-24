package com.regisoc.modules.players.infrastructure.web

import com.regisoc.modules.players.domain.Player
import com.regisoc.modules.players.domain.PlayerPosition
import java.time.LocalDate
import java.time.LocalDateTime

data class PlayerResponse(
    val id: String,
    val clubId: String,
    val name: String,
    val dateOfBirth: LocalDate?,
    val position: PlayerPosition?,
    val photoUrl: String?,
    val documentFrontUrl: String?,
    val documentBackUrl: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(player: Player) = PlayerResponse(
            id = player.id,
            clubId = player.club.id,
            name = player.name,
            dateOfBirth = player.dateOfBirth,
            position = player.position,
            photoUrl = player.photoUrl,
            documentFrontUrl = player.documentFrontUrl,
            documentBackUrl = player.documentBackUrl,
            createdAt = player.createdAt,
            updatedAt = player.updatedAt
        )
    }
}

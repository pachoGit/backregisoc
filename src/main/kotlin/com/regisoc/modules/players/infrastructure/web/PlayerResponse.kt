package com.regisoc.modules.players.infrastructure.web

import com.regisoc.modules.players.domain.Player
import com.regisoc.modules.players.domain.PlayerPosition
import java.time.LocalDate
import java.time.LocalDateTime

data class PlayerResponse(
    val id: Long,
    val clubId: Long,
    val firstName: String,
    val lastName: String,
    val documentNumber: String,
    val age: Int,
    val dateOfBirth: LocalDate,
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
            firstName = player.firstName,
            lastName = player.lastName,
            documentNumber = player.documentNumber,
            age = player.age,
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

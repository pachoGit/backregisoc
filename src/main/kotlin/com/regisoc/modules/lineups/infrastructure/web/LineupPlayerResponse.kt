package com.regisoc.modules.lineups.infrastructure.web

import com.regisoc.modules.lineups.domain.LineupPlayer
import com.regisoc.modules.players.domain.PlayerPosition
import java.time.LocalDate

data class LineupPlayerResponse(
    val playerId: Long,
    val firstName: String,
    val lastName: String,
    val documentNumber: String,
    val age: Int,
    val dateOfBirth: LocalDate,
    val position: PlayerPosition?,
    val photoUrl: String?,
    val documentFrontUrl: String?,
    val documentBackUrl: String?
) {
    companion object {
        fun from(lineupPlayer: LineupPlayer) = LineupPlayerResponse(
            playerId = lineupPlayer.playerId,
            firstName = lineupPlayer.firstName,
            lastName = lineupPlayer.lastName,
            documentNumber = lineupPlayer.documentNumber,
            age = lineupPlayer.age,
            dateOfBirth = lineupPlayer.dateOfBirth,
            position = lineupPlayer.position,
            photoUrl = lineupPlayer.photoUrl,
            documentFrontUrl = lineupPlayer.documentFrontUrl,
            documentBackUrl = lineupPlayer.documentBackUrl
        )
    }
}

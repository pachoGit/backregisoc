package com.regisoc.modules.lineups.domain

import com.regisoc.modules.players.domain.Player
import com.regisoc.modules.players.domain.PlayerPosition
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDate

/**
 * Copia (snapshot) de los datos de un jugador al momento de completar la plantilla.
 *
 * Al ser un value object inmutable, no guarda referencia al [Player] original,
 * por lo que la plantilla conserva los datos tal cual se inscribieron.
 */
@Embeddable
class LineupPlayer(
    playerId: Long,
    firstName: String,
    lastName: String,
    documentNumber: String,
    age: Int,
    dateOfBirth: LocalDate,
    position: PlayerPosition?,
    photoUrl: String?,
    documentFrontUrl: String?,
    documentBackUrl: String?
) {
    @Column(name = "player_id", nullable = false)
    val playerId: Long = playerId

    @Column(name = "first_name", nullable = false)
    val firstName: String = firstName

    @Column(name = "last_name", nullable = false)
    val lastName: String = lastName

    @Column(name = "document_number", nullable = false)
    val documentNumber: String = documentNumber

    @Column(name = "age", nullable = false)
    val age: Int = age

    @Column(name = "date_of_birth", nullable = false)
    val dateOfBirth: LocalDate = dateOfBirth

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    val position: PlayerPosition? = position

    @Column(name = "photo_url", columnDefinition = "text")
    val photoUrl: String? = photoUrl

    @Column(name = "document_front_url", columnDefinition = "text")
    val documentFrontUrl: String? = documentFrontUrl

    @Column(name = "document_back_url", columnDefinition = "text")
    val documentBackUrl: String? = documentBackUrl

    companion object {
        fun from(player: Player) = LineupPlayer(
            playerId = player.id,
            firstName = player.firstName,
            lastName = player.lastName,
            documentNumber = player.documentNumber,
            age = player.age,
            dateOfBirth = player.dateOfBirth,
            position = player.position,
            photoUrl = player.photoUrl,
            documentFrontUrl = player.documentFrontUrl,
            documentBackUrl = player.documentBackUrl
        )
    }
}

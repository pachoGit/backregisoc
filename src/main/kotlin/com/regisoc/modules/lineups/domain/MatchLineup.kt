package com.regisoc.modules.lineups.domain

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.matches.domain.Match
import com.regisoc.modules.players.domain.Player
import com.regisoc.shared.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

/**
 * Entidad que representa la alineación de un jugador en un partido.
 *
 * Asigna un jugador a un club dentro de un partido específico,
 * formando parte de la plantilla que disputará el encuentro.
 *
 * @property match Partido al que pertenece la alineación.
 * @property club Club al que representa el jugador en ese partido.
 * @property player Jugador convocado.
 */
@Entity
@Table(name = "match_lineups")
class MatchLineup(
    match: Match,
    club: Club,
    player: Player
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    var match: Match = match
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    var club: Club = club
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    var player: Player = player
        protected set
}

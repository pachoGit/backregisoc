package com.regisoc.modules.matches.domain

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.lineups.domain.MatchLineup
import com.regisoc.modules.matchdates.domain.MatchDate
import com.regisoc.shared.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * Entidad que representa un partido entre dos clubes.
 *
 * Un partido pertenece a una jornada ([MatchDate]) dentro de un evento,
 * enfrenta a dos clubes (local y visitante) y tiene un estado que determina
 * en qué fase del ciclo de vida se encuentra.
 *
 */
@Entity
@Table(name = "matches")
class Match(
    matchDate: MatchDate,
    homeClub: Club,
    awayClub: Club,
    scheduledTime: LocalDateTime,
    status: MatchStatus = MatchStatus.UPCOMING
) : BaseEntity() {

    /**
     * @property matchDate Jornada a la que pertenece el partido.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_date_id")
    var matchDate: MatchDate = matchDate
        protected set

    /**
     * @property homeClub Club local.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_club_id")
    var homeClub: Club = homeClub
        protected set

    /**
     * @property awayClub Club visitante.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_club_id")
    var awayClub: Club = awayClub
        protected set

    /**
     * @property scheduledTime Fecha y hora programada para la disputa del partido.
     */
    var scheduledTime: LocalDateTime = scheduledTime
        protected set

    /**
     * @property status Estado actual del partido ([MatchStatus]).
     */
    @Enumerated(EnumType.STRING)
    var status: MatchStatus = status
        protected set

    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY)
    var lineups: MutableList<MatchLineup> = mutableListOf()
        protected set

    /**
     * Cambia el estado del partido.
     *
     * @param status Nuevo estado del partido ([MatchStatus]).
     */
    fun changeStatus(status: MatchStatus) {
        this.status = status
        this.updatedAt = LocalDateTime.now()
    }

    /**
     * Re-programa la fecha y hora del partido.
     *
     * @param scheduledTime Nueva fecha y hora programada.
     */
    fun reschedule(scheduledTime: LocalDateTime) {
        this.scheduledTime = scheduledTime
        this.updatedAt = LocalDateTime.now()
    }
}

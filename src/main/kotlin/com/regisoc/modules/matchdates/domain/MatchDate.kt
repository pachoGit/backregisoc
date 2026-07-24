package com.regisoc.modules.matchdates.domain

import com.regisoc.modules.events.domain.Event
import com.regisoc.modules.matches.domain.Match
import com.regisoc.shared.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Entidad que representa una jornada o fecha dentro de un evento.
 *
 * Cada jornada agrupa varios partidos ([Match]) que se disputan en una misma
 * fecha dentro del calendario de un evento o torneo.
 *
 * @property event Evento al que pertenece la jornada.
 * @property name Nombre identificativo de la jornada (ej. "Fecha 1", "Semifinales").
 * @property date Fecha calendario en la que se disputa la jornada.
 */
@Entity
@Table(name = "match_dates")
class MatchDate(
    event: Event,
    name: String,
    date: LocalDate
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    var event: Event = event
        protected set

    @OneToMany(mappedBy = "matchDate", fetch = FetchType.LAZY)
    var matches: MutableList<Match> = mutableListOf()
        protected set

    var name: String = name
        protected set
    var date: LocalDate = date
        protected set

    /**
     * Actualiza los datos de la jornada.
     *
     * @param name Nuevo nombre de la jornada.
     * @param date Nueva fecha de la jornada.
     */
    fun update(name: String, date: LocalDate) {
        this.name = name
        this.date = date
        this.updatedAt = LocalDateTime.now()
    }
}

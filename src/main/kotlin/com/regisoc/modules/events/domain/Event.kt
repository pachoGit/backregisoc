package com.regisoc.modules.events.domain

import com.regisoc.modules.matchdates.domain.MatchDate
import com.regisoc.shared.domain.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Entidad que representa un evento o torneo deportivo.
 *
 * Un evento agrupa un conjunto de jornadas ([MatchDate]) y puede tener diferentes
 * estados a lo largo de su ciclo de vida: próximo, en curso o finalizado.
 *
 * @property name Nombre del evento o torneo (ej. "Torneo Apertura 2025").
 * @property description Descripción o información adicional del evento (opcional).
 * @property startDate Fecha de inicio del evento.
 * @property endDate Fecha de finalización del evento.
 * @property status Estado actual del evento ([EventStatus]).
 */
@Entity
@Table(name = "events")
class Event(
    name: String,
    description: String? = null,
    startDate: LocalDate,
    endDate: LocalDate,
    status: EventStatus = EventStatus.UPCOMING
) : BaseEntity() {
    var name: String = name
        protected set
    var description: String? = description
        protected set
    var startDate: LocalDate = startDate
        protected set
    var endDate: LocalDate = endDate
        protected set

    @Enumerated(EnumType.STRING)
    var status: EventStatus = status
        protected set

    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var registrations: MutableList<EventRegistration> = mutableListOf()
        protected set

    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var matchDates: MutableList<MatchDate> = mutableListOf()
        protected set

    /**
     * Actualiza los datos generales del evento.
     *
     * @param name Nuevo nombre del evento.
     * @param description Nueva descripción (puede ser `null`).
     * @param startDate Nueva fecha de inicio.
     * @param endDate Nueva fecha de finalización.
     */
    fun update(name: String, description: String?, startDate: LocalDate, endDate: LocalDate) {
        this.name = name
        this.description = description
        this.startDate = startDate
        this.endDate = endDate
        this.updatedAt = LocalDateTime.now()
    }

    /**
     * Cambia el estado del evento.
     *
     * @param status Nuevo estado del evento ([EventStatus]).
     */
    fun changeStatus(status: EventStatus) {
        this.status = status
        this.updatedAt = LocalDateTime.now()
    }

    /**
     * Agrega una jornada a este evento.
     *
     * @param matchDate Jornada a agregar.
     */
    fun addMatchDate(matchDate: MatchDate) {
        matchDates.add(matchDate)
    }
}

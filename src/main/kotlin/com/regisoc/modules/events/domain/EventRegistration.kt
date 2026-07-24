package com.regisoc.modules.events.domain

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.shared.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * Entidad que representa la inscripción de un club en un evento.
 *
 * Vincula un club a un evento específico, registrando el momento en que se realizó la inscripción.
 *
 * @property event Evento al que se inscribe el club.
 * @property club Club que se inscribe en el evento.
 * @property registeredAt Fecha y hora en que se realizó la inscripción.
 */
@Entity
@Table(name = "event_registrations")
class EventRegistration(
    event: Event,
    club: Club,
    registeredAt: LocalDateTime = LocalDateTime.now()
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    var event: Event = event
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    var club: Club = club
        protected set

    var registeredAt: LocalDateTime = registeredAt
        protected set
}

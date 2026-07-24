package com.regisoc.modules.events.infrastructure.persistence

import com.regisoc.modules.events.domain.EventRegistration
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface EventRegistrationJpaRepository : JpaRepository<EventRegistration, String> {
    fun findByEventId(eventId: String): List<EventRegistration>
    fun findByEventIdAndClubId(eventId: String, clubId: String): Optional<EventRegistration>
    fun findByClubId(clubId: String): List<EventRegistration>
}

package com.regisoc.modules.events.infrastructure.persistence

import com.regisoc.modules.events.domain.EventRegistration
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface EventRegistrationJpaRepository : JpaRepository<EventRegistration, Long> {
    fun findByEventId(eventId: Long): List<EventRegistration>
    fun findByEventIdAndClubId(eventId: Long, clubId: Long): Optional<EventRegistration>
    fun findByClubId(clubId: Long): List<EventRegistration>
}

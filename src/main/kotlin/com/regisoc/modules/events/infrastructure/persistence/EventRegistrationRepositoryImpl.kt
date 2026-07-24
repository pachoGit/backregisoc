package com.regisoc.modules.events.infrastructure.persistence

import com.regisoc.modules.events.domain.EventRegistration
import com.regisoc.modules.events.domain.EventRegistrationRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class EventRegistrationRepositoryImpl(
    private val jpaRepository: EventRegistrationJpaRepository
) : EventRegistrationRepository {
    override fun save(registration: EventRegistration): EventRegistration = jpaRepository.save(registration)
    override fun findById(id: Long): Optional<EventRegistration> = jpaRepository.findById(id)
    override fun findByEventId(eventId: Long): List<EventRegistration> = jpaRepository.findByEventId(eventId)
    override fun findByEventIdAndClubId(eventId: Long, clubId: Long): Optional<EventRegistration> =
        jpaRepository.findByEventIdAndClubId(eventId, clubId)
    override fun findByClubId(clubId: Long): List<EventRegistration> = jpaRepository.findByClubId(clubId)
    override fun delete(registration: EventRegistration) = jpaRepository.delete(registration)
}

package com.regisoc.modules.events.infrastructure.persistence

import com.regisoc.modules.events.domain.Event
import com.regisoc.modules.events.domain.EventRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class EventRepositoryImpl(
    private val jpaRepository: EventJpaRepository
) : EventRepository {
    override fun save(event: Event): Event = jpaRepository.save(event)
    override fun findById(id: Long): Optional<Event> = jpaRepository.findById(id)
    override fun findAll(): List<Event> = jpaRepository.findAll()
    override fun findActive(clubId: Long?): List<Event> = jpaRepository.findActive(clubId)
    override fun delete(event: Event) = jpaRepository.delete(event)
}

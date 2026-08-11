package com.regisoc.modules.events.infrastructure.persistence

import com.regisoc.modules.events.domain.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface EventJpaRepository : JpaRepository<Event, Long> {
    @Query(
        "SELECT DISTINCT e FROM Event e JOIN e.registrations r " +
            "WHERE e.deletedAt IS NULL AND (:clubId IS NULL OR r.club.id = :clubId)"
    )
    fun findActive(@Param("clubId") clubId: Long?): List<Event>
}

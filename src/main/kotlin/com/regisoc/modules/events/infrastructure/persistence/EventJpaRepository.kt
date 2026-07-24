package com.regisoc.modules.events.infrastructure.persistence

import com.regisoc.modules.events.domain.Event
import org.springframework.data.jpa.repository.JpaRepository

interface EventJpaRepository : JpaRepository<Event, String>

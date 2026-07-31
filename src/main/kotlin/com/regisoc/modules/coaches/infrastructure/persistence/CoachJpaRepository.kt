package com.regisoc.modules.coaches.infrastructure.persistence

import com.regisoc.modules.coaches.domain.Coach
import org.springframework.data.jpa.repository.JpaRepository

interface CoachJpaRepository : JpaRepository<Coach, Long> {
    fun findAllByClubId(clubId: Long): List<Coach>
}

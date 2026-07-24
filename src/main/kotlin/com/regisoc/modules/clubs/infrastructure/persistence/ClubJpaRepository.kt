package com.regisoc.modules.clubs.infrastructure.persistence

import com.regisoc.modules.clubs.domain.Club
import org.springframework.data.jpa.repository.JpaRepository

interface ClubJpaRepository : JpaRepository<Club, String> {
    fun findByNameContaining(name: String): List<Club>
}

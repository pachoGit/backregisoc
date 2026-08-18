package com.regisoc.modules.clubs.infrastructure.persistence

import com.regisoc.modules.clubs.domain.Club
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface ClubJpaRepository : JpaRepository<Club, Long> {
    fun findByNameContaining(name: String): List<Club>

    @Query("""
        SELECT c FROM Club c
        WHERE c.id = :id
        AND c.isActive = true
           """)
    fun findActiveById(@Param("id") id: Long): Optional<Club>

    @Query("""
        SELECT c FROM Club c
        WHERE c.isActive = true
           """)
    fun findActiveAll(): List<Club>
}

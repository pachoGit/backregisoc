package com.regisoc.modules.players.infrastructure.persistence

import com.regisoc.modules.players.domain.Player
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface PlayerJpaRepository : JpaRepository<Player, Long> {
    fun findAllByClubIdAndDeletedAtIsNull(clubId: Long): List<Player>

    @Query("SELECT p FROM Player p WHERE p.id = :id AND p.deletedAt IS NULL")
    fun findByIdAndDeletedAtIsNull(@Param("id") id: Long): Optional<Player>
}

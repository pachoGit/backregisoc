package com.regisoc.modules.players.infrastructure.persistence

import com.regisoc.modules.players.domain.Player
import org.springframework.data.jpa.repository.JpaRepository

interface PlayerJpaRepository : JpaRepository<Player, String> {
    fun findAllByClubId(clubId: String): List<Player>
}

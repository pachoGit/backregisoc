package com.regisoc.modules.players.infrastructure.persistence

import com.regisoc.modules.players.domain.Player
import com.regisoc.modules.players.domain.PlayerRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class PlayerRepositoryImpl(
    private val jpaRepository: PlayerJpaRepository
) : PlayerRepository {
    override fun save(player: Player): Player = jpaRepository.save(player)
    override fun findById(id: String): Optional<Player> = jpaRepository.findById(id)
    override fun findAllByClubId(clubId: String): List<Player> = jpaRepository.findAllByClubId(clubId)
    override fun delete(player: Player) = jpaRepository.delete(player)
}

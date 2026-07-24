package com.regisoc.modules.players.application

import com.regisoc.modules.players.domain.Player
import com.regisoc.modules.players.domain.PlayerRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class GetPlayerUseCase(private val repository: PlayerRepository) {
    fun findById(id: Long): Player {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException("Player not found with id: $id") }
    }

    fun findByClub(clubId: Long): List<Player> = repository.findAllByClubId(clubId)
}

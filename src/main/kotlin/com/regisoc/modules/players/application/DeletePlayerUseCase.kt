package com.regisoc.modules.players.application

import com.regisoc.modules.players.domain.PlayerRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class DeletePlayerUseCase(private val repository: PlayerRepository) {
    fun execute(id: Long) {
        val player = repository.findById(id)
            .orElseThrow { EntityNotFoundException("Player not found with id: $id") }
        player.delete()
        repository.save(player)
    }
}

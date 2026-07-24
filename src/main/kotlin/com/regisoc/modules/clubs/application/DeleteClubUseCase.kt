package com.regisoc.modules.clubs.application

import com.regisoc.modules.clubs.domain.ClubRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class DeleteClubUseCase(private val repository: ClubRepository) {
    fun execute(id: Long) {
        val club = repository.findById(id)
            .orElseThrow { EntityNotFoundException("Club not found with id: $id") }
        club.deactivate()
        repository.save(club)
    }
}

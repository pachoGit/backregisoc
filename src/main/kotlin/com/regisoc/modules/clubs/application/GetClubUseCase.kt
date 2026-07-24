package com.regisoc.modules.clubs.application

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.clubs.domain.ClubRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class GetClubUseCase(private val repository: ClubRepository) {
    fun findById(id: Long): Club {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException("Club not found with id: $id") }
    }

    fun findAll(): List<Club> = repository.findAll()

    fun searchByName(name: String): List<Club> = repository.findByNameContaining(name)
}

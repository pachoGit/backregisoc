package com.regisoc.modules.coaches.application

import com.regisoc.modules.coaches.domain.Coach
import com.regisoc.modules.coaches.domain.CoachRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class GetCoachUseCase(private val repository: CoachRepository) {
    fun findById(id: Long): Coach {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException("Coach not found with id: $id") }
    }

    fun findByClub(clubId: Long): List<Coach> = repository.findAllByClubId(clubId)
}

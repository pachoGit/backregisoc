package com.regisoc.modules.physicaltrainers.application

import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainer
import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainerRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class GetPhysicalTrainerUseCase(private val repository: PhysicalTrainerRepository) {
    fun findById(id: Long): PhysicalTrainer {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException("PhysicalTrainer not found with id: $id") }
    }

    fun findByClub(clubId: Long): List<PhysicalTrainer> = repository.findAllByClubId(clubId)
}

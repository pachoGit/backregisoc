package com.regisoc.modules.physicaltrainers.application

import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainerRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class DeletePhysicalTrainerUseCase(private val repository: PhysicalTrainerRepository) {
    fun execute(id: Long) {
        val physicalTrainer = repository.findById(id)
            .orElseThrow { EntityNotFoundException("PhysicalTrainer not found with id: $id") }
        repository.delete(physicalTrainer)
    }
}

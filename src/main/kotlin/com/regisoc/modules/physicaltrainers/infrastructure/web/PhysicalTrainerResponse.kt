package com.regisoc.modules.physicaltrainers.infrastructure.web

import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainer
import java.time.LocalDate
import java.time.LocalDateTime

data class PhysicalTrainerResponse(
    val id: Long,
    val clubId: Long,
    val firstName: String,
    val lastName: String,
    val documentNumber: String,
    val age: Int,
    val dateOfBirth: LocalDate,
    val photoUrl: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(physicalTrainer: PhysicalTrainer) = PhysicalTrainerResponse(
            id = physicalTrainer.id,
            clubId = physicalTrainer.club.id,
            firstName = physicalTrainer.firstName,
            lastName = physicalTrainer.lastName,
            documentNumber = physicalTrainer.documentNumber,
            age = physicalTrainer.age,
            dateOfBirth = physicalTrainer.dateOfBirth,
            photoUrl = physicalTrainer.photoUrl,
            createdAt = physicalTrainer.createdAt,
            updatedAt = physicalTrainer.updatedAt
        )
    }
}

package com.regisoc.modules.lineups.infrastructure.web

import com.regisoc.modules.lineups.domain.LineupPhysicalTrainer
import java.time.LocalDate

data class LineupPhysicalTrainerResponse(
    val physicalTrainerId: Long,
    val firstName: String,
    val lastName: String,
    val documentNumber: String,
    val age: Int,
    val dateOfBirth: LocalDate,
    val photoUrl: String?,
    val documentFrontUrl: String?,
    val documentBackUrl: String?
) {
    companion object {
        fun from(lineupPhysicalTrainer: LineupPhysicalTrainer) = LineupPhysicalTrainerResponse(
            physicalTrainerId = lineupPhysicalTrainer.physicalTrainerId,
            firstName = lineupPhysicalTrainer.firstName,
            lastName = lineupPhysicalTrainer.lastName,
            documentNumber = lineupPhysicalTrainer.documentNumber,
            age = lineupPhysicalTrainer.age,
            dateOfBirth = lineupPhysicalTrainer.dateOfBirth,
            photoUrl = lineupPhysicalTrainer.photoUrl,
            documentFrontUrl = lineupPhysicalTrainer.documentFrontUrl,
            documentBackUrl = lineupPhysicalTrainer.documentBackUrl
        )
    }
}

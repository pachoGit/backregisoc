package com.regisoc.modules.lineups.infrastructure.web

import com.regisoc.modules.lineups.domain.LineupCoach
import java.time.LocalDate

data class LineupCoachResponse(
    val coachId: Long,
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
        fun from(lineupCoach: LineupCoach) = LineupCoachResponse(
            coachId = lineupCoach.coachId,
            firstName = lineupCoach.firstName,
            lastName = lineupCoach.lastName,
            documentNumber = lineupCoach.documentNumber,
            age = lineupCoach.age,
            dateOfBirth = lineupCoach.dateOfBirth,
            photoUrl = lineupCoach.photoUrl,
            documentFrontUrl = lineupCoach.documentFrontUrl,
            documentBackUrl = lineupCoach.documentBackUrl
        )
    }
}

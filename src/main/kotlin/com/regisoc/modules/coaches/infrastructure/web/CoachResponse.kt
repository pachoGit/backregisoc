package com.regisoc.modules.coaches.infrastructure.web

import com.regisoc.modules.coaches.domain.Coach
import java.time.LocalDate
import java.time.LocalDateTime

data class CoachResponse(
    val id: Long,
    val clubId: Long,
    val firstName: String,
    val lastName: String,
    val documentNumber: String,
    val age: Int,
    val dateOfBirth: LocalDate,
    val photoUrl: String?,
    val documentFrontUrl: String?,
    val documentBackUrl: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(coach: Coach) = CoachResponse(
            id = coach.id,
            clubId = coach.club.id,
            firstName = coach.firstName,
            lastName = coach.lastName,
            documentNumber = coach.documentNumber,
            age = coach.age,
            dateOfBirth = coach.dateOfBirth,
            photoUrl = coach.photoUrl,
            documentFrontUrl = coach.documentFrontUrl,
            documentBackUrl = coach.documentBackUrl,
            createdAt = coach.createdAt,
            updatedAt = coach.updatedAt
        )
    }
}

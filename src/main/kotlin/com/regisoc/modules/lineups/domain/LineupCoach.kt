package com.regisoc.modules.lineups.domain

import com.regisoc.modules.coaches.domain.Coach
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.LocalDate

/**
 * Copia (snapshot) de los datos de un entrenador al momento de completar la plantilla.
 *
 * Al ser un value object inmutable, no guarda referencia al [Coach] original,
 * por lo que la plantilla conserva los datos tal cual se inscribieron.
 */
@Embeddable
class LineupCoach(
    coachId: Long,
    firstName: String,
    lastName: String,
    documentNumber: String,
    age: Int,
    dateOfBirth: LocalDate,
    photoUrl: String?
) {
    @Column(name = "coach_id", nullable = false)
    val coachId: Long = coachId

    @Column(name = "first_name", nullable = false)
    val firstName: String = firstName

    @Column(name = "last_name", nullable = false)
    val lastName: String = lastName

    @Column(name = "document_number", nullable = false)
    val documentNumber: String = documentNumber

    @Column(name = "age", nullable = false)
    val age: Int = age

    @Column(name = "date_of_birth", nullable = false)
    val dateOfBirth: LocalDate = dateOfBirth

    @Column(name = "photo_url", columnDefinition = "text")
    val photoUrl: String? = photoUrl

    companion object {
        fun from(coach: Coach) = LineupCoach(
            coachId = coach.id,
            firstName = coach.firstName,
            lastName = coach.lastName,
            documentNumber = coach.documentNumber,
            age = coach.age,
            dateOfBirth = coach.dateOfBirth,
            photoUrl = coach.photoUrl
        )
    }
}

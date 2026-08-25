package com.regisoc.modules.lineups.domain

import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainer
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.LocalDate

/**
 * Copia (snapshot) de los datos de un preparador físico al momento de completar la plantilla.
 *
 * Al ser un value object inmutable, no guarda referencia al [PhysicalTrainer] original,
 * por lo que la plantilla conserva los datos tal cual se inscribieron.
 */
@Embeddable
class LineupPhysicalTrainer(
    physicalTrainerId: Long,
    firstName: String,
    lastName: String,
    documentNumber: String,
    age: Int,
    dateOfBirth: LocalDate,
    photoUrl: String?,
    documentFrontUrl: String?,
    documentBackUrl: String?
) {
    @Column(name = "physical_trainer_id", nullable = false)
    val physicalTrainerId: Long = physicalTrainerId

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

    @Column(name = "document_front_url", columnDefinition = "text")
    val documentFrontUrl: String? = documentFrontUrl

    @Column(name = "document_back_url", columnDefinition = "text")
    val documentBackUrl: String? = documentBackUrl

    companion object {
        fun from(physicalTrainer: PhysicalTrainer) = LineupPhysicalTrainer(
            physicalTrainerId = physicalTrainer.id,
            firstName = physicalTrainer.firstName,
            lastName = physicalTrainer.lastName,
            documentNumber = physicalTrainer.documentNumber,
            age = physicalTrainer.age,
            dateOfBirth = physicalTrainer.dateOfBirth,
            photoUrl = physicalTrainer.photoUrl,
            documentFrontUrl = physicalTrainer.documentFrontUrl,
            documentBackUrl = physicalTrainer.documentBackUrl
        )
    }
}

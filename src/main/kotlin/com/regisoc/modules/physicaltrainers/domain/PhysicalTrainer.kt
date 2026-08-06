package com.regisoc.modules.physicaltrainers.domain

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.shared.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "physical_trainers")
class PhysicalTrainer(
    club: Club,
    firstName: String,
    lastName: String,
    documentNumber: String,
    age: Int,
    dateOfBirth: LocalDate,
    photoUrl: String? = null,
    documentFrontUrl: String? = null,
    documentBackUrl: String? = null
) : BaseEntity() {

    @Column(nullable = false)
    var firstName: String = firstName
        protected set

    @Column(nullable = false)
    var lastName: String = lastName
        protected set

    @Column(nullable = false)
    var documentNumber: String = documentNumber
        protected set

    @Column(nullable = false)
    var age: Int = age
        protected set

    @Column(nullable = false)
    var dateOfBirth: LocalDate = dateOfBirth
        protected set

    @Column(columnDefinition = "text")
    var photoUrl: String? = photoUrl
        protected set

    @Column(columnDefinition = "text")
    var documentFrontUrl: String? = documentFrontUrl
        protected set

    @Column(columnDefinition = "text")
    var documentBackUrl: String? = documentBackUrl
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    var club: Club = club
        protected set

    fun update(
        firstName: String,
        lastName: String,
        documentNumber: String,
        age: Int,
        dateOfBirth: LocalDate,
        photoUrl: String?,
        documentFrontUrl: String?,
        documentBackUrl: String?
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.documentNumber = documentNumber
        this.age = age
        this.dateOfBirth = dateOfBirth
        this.photoUrl = photoUrl
        this.documentFrontUrl = documentFrontUrl
        this.documentBackUrl = documentBackUrl
        this.updatedAt = LocalDateTime.now()
    }
}

package com.regisoc.modules.users.domain

import com.regisoc.shared.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Column
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    name: String,
    surname: String,
    documentNumber: String,
    username: String,
    password: String,
    role: UserRole,
    clubId: Long? = null
) : BaseEntity() {

    var name: String = name; protected set
    var surname: String = surname; protected set
    var documentNumber: String = documentNumber; protected set

    @Column(unique = true)
    var username: String = username; protected set

    var password: String = password; protected set

    @Enumerated(EnumType.STRING)
    var role: UserRole = role; protected set

    var clubId: Long? = clubId; protected set

    var isActive: Boolean = true; protected set

    init {
        require(clubId == null || role == UserRole.CLUB_MANAGER) {
            "Only CLUB_MANAGER users can be associated with a club"
        }
    }

    fun update(name: String, surname: String, documentNumber: String) {
        this.name = name
        this.surname = surname
        this.documentNumber = documentNumber
        this.updatedAt = LocalDateTime.now()
    }

    fun deactivate() {
        this.isActive = false
        this.updatedAt = LocalDateTime.now()
        this.deletedAt = LocalDateTime.now()
    }
}

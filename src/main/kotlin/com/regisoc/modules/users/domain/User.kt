package com.regisoc.modules.users.domain

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.shared.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.Column
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import java.time.LocalDateTime

/**
 * Entidad que representa un usuario.
 */
@Entity
@Table(name = "users")
class User(
    name: String,
    surname: String,
    documentNumber: String,
    username: String,
    password: String,
    role: UserRole,
    club: Club? = null
) : BaseEntity() {

    /**
     * @property name Nombre del usuario.
     */
    var name: String = name
        protected set

    /**
     * @property surname Apellido del usuario.
     */
    var surname: String = surname
        protected set

    /**
     * @property documentNumber Numero de documento del usuario.
     */
    var documentNumber: String = documentNumber
        protected set

    /**
     * @property username Nombre de usuario del usuario.
     */
    @Column(unique = true)
    var username: String = username
        protected set

    /**
     * @property password Contraseña del usuario.
     */
    var password: String = password
        protected set

    /**
     * @property role Rol del usuario.
     */
    @Enumerated(EnumType.STRING)
    var role: UserRole = role
        protected set

    /**
     * @property club Club que gestiona el usuario.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    var club: Club? = club
        protected set

    /**
     * @property isActive Flag que indica si el usuario esta activo.
     */
    var isActive: Boolean = true; protected set

    init {
        require(club == null || role == UserRole.CLUB_MANAGER) {
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

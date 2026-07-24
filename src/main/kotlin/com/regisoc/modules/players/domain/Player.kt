package com.regisoc.modules.players.domain

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.lineups.domain.MatchLineup
import com.regisoc.shared.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Column
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Entidad que representa un jugador registrado en el sistema.
 *
 * Almacena los datos personales del jugador, su posición en el campo,
 * y las URLs de su foto y documentos de identificación.
 */
@Entity
@Table(name = "players")
class Player(
    club: Club,
    firstName: String,
    lastName: String,
    documentNumber: String,
    age: Int,
    dateOfBirth: LocalDate,
    position: PlayerPosition? = null,
    photoUrl: String? = null,
    documentFrontUrl: String? = null,
    documentBackUrl: String? = null
) : BaseEntity() {

    /**
     * @property firstName Nombre completo del jugador.
     */
    @Column(nullable=false)
    var firstName: String = firstName
        protected set

    /**
     * @property lastName Apellido completo del jugador.
     */
    @Column(nullable=false)
    var lastName: String = lastName
        protected set

    /**
     * @property documentNumber Numero de documento del jugador.
     */
    @Column(nullable=false)
    var documentNumber: String = documentNumber
        protected set

    /**
     * @property age Edad del jugador.
     */
    @Column(nullable=false)
    var age: Int = age
        protected set

    /**
     * @property dateOfBirth Fecha de nacimiento del jugador.
     */
    @Column(nullable=false)
    var dateOfBirth: LocalDate = dateOfBirth
        protected set

    /**
     * @property position Posición en el campo de juego ([PlayerPosition], opcional).
     */
    @Enumerated(EnumType.STRING)
    var position: PlayerPosition? = position
        protected set

    /**
     * @property photoUrl URL pública de la foto del jugador (opcional).
     */
    @Column(columnDefinition="text")
    var photoUrl: String? = photoUrl
        protected set

    /**
     * @property documentFrontUrl URL pública de la imagen frontal del documento de identidad (opcional).
     */
    @Column(columnDefinition="text")
    var documentFrontUrl: String? = documentFrontUrl
        protected set

    /**
     * @property documentBackUrl URL pública de la imagen posterior del documento de identidad (opcional).
     */
    @Column(columnDefinition="text")
    var documentBackUrl: String? = documentBackUrl
        protected set

    /**
     * @property club Club al que pertenece el jugador.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    var club: Club = club
        protected set

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
    var lineups: MutableList<MatchLineup> = mutableListOf()
        protected set

    /**
     * Actualiza todos los datos editables del jugador.
     *
     * @param name Nuevo nombre completo.
     * @param dateOfBirth Nueva fecha de nacimiento (puede ser `null`).
     * @param position Nueva posición en el campo (puede ser `null`).
     * @param photoUrl Nueva URL de la foto (puede ser `null`).
     * @param documentFrontUrl Nueva URL del frontal del documento (puede ser `null`).
     * @param documentBackUrl Nueva URL del dorso del documento (puede ser `null`).
     */
    fun update(
        firstName: String,
        lastName: String,
        documentNumber: String,
        age: Int,
        dateOfBirth: LocalDate,
        position: PlayerPosition?,
        photoUrl: String?,
        documentFrontUrl: String?,
        documentBackUrl: String?
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.documentNumber = documentNumber
        this.age = age
        this.dateOfBirth = dateOfBirth
        this.position = position
        this.photoUrl = photoUrl
        this.documentFrontUrl = documentFrontUrl
        this.documentBackUrl = documentBackUrl
        this.updatedAt = LocalDateTime.now()
    }

    /**
     * Actualiza exclusivamente las URLs de las fotos y documentos del jugador.
     *
     * @param photoUrl Nueva URL de la foto (puede ser `null`).
     * @param documentFrontUrl Nueva URL del frontal del documento (puede ser `null`).
     * @param documentBackUrl Nueva URL del dorso del documento (puede ser `null`).
     */
    fun updatePhotos(photoUrl: String?, documentFrontUrl: String?, documentBackUrl: String?) {
        this.photoUrl = photoUrl
        this.documentFrontUrl = documentFrontUrl
        this.documentBackUrl = documentBackUrl
        this.updatedAt = LocalDateTime.now()
    }
}

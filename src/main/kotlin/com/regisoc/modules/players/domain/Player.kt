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
    name: String,
    dateOfBirth: LocalDate? = null,
    position: PlayerPosition? = null,
    photoUrl: String? = null,
    documentFrontUrl: String? = null,
    documentBackUrl: String? = null
) : BaseEntity() {

    /**
     * @property name Nombre completo del jugador.
     */
    var name: String = name
        protected set

    /**
     * @property dateOfBirth Fecha de nacimiento del jugador (opcional).
     */
    var dateOfBirth: LocalDate? = dateOfBirth
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
    var photoUrl: String? = photoUrl
        protected set

    /**
     * @property documentFrontUrl URL pública de la imagen frontal del documento de identidad (opcional).
     */
    var documentFrontUrl: String? = documentFrontUrl
        protected set

    /**
     * @property documentBackUrl URL pública de la imagen posterior del documento de identidad (opcional).
     */
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
        name: String,
        dateOfBirth: LocalDate?,
        position: PlayerPosition?,
        photoUrl: String?,
        documentFrontUrl: String?,
        documentBackUrl: String?
    ) {
        this.name = name
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

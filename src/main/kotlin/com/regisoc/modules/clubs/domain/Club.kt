package com.regisoc.modules.clubs.domain

import com.regisoc.modules.events.domain.EventRegistration
import com.regisoc.modules.lineups.domain.MatchLineup
import com.regisoc.modules.matches.domain.Match
import com.regisoc.modules.players.domain.Player
import com.regisoc.shared.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Column
import java.time.LocalDateTime

/**
 * Entidad que representa un club deportivo.
 *
 * Almacena la información básica del club, incluyendo su nombre, año de fundación,
 * escudo, descripción y estado de actividad.
 */
@Entity
@Table(name = "clubs")
class Club(
    name: String,
    foundedYear: Int? = null,
    crestUrl: String? = null,
    description: String? = null,
    createdBy: String,
    isActive: Boolean = true
) : BaseEntity() {

    /**
     * @property name Nombre oficial del club deportivo.
     */
    var name: String = name
        protected set

    /**
     * @property foundedYear Año de fundación del club (opcional).
     */
    var foundedYear: Int? = foundedYear
        protected set

    /**
     * @property crestUrl URL pública del escudo o emblema del club (opcional).
     */
    @Column(columnDefinition="text")
    var crestUrl: String? = crestUrl
        protected set

    /**
     * @property description Descripción o reseña histórica del club (opcional).
     */
    @Column(columnDefinition="text")
    var description: String? = description
        protected set

    /**
     * @property createdBy Identificador del usuario que creó el registro del club.
     */
    var createdBy: String = createdBy
        protected set

    /**
     * @property isActive Indica si el club se encuentra activo en el sistema (por defecto `true`).
     *                     Un club desactivado no debería aparecer en selecciones ni partidos activos.
     */
    var isActive: Boolean = isActive
        protected set

    /**
     * @property players Lista de jugadores del club.
     */
    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    var players: MutableList<Player> = mutableListOf()
        protected set

    /**
     * @property eventRegistrations Lista de eventos a los esta registrado el club.
     */
    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    var eventRegistrations: MutableList<EventRegistration> = mutableListOf()
        protected set

    /**
     * @property matchLineups Lista de plantillas que ha registrado el club.
     */
    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    var matchLineups: MutableList<MatchLineup> = mutableListOf()
        protected set

    /**
     * @property homeMatches Lista de partidos en los que el club ha sido local.
     */
    @OneToMany(mappedBy = "homeClub", fetch = FetchType.LAZY)
    var homeMatches: MutableList<Match> = mutableListOf()
        protected set

    /**
     * @property awayMatches Lista de partidos en los que el club ha sido visitante.
     */
    @OneToMany(mappedBy = "awayClub", fetch = FetchType.LAZY)
    var awayMatches: MutableList<Match> = mutableListOf()
        protected set

    /**
     * Actualiza los datos generales del club.
     *
     * @param name Nuevo nombre del club.
     * @param foundedYear Nuevo año de fundación (puede ser `null`).
     * @param crestUrl Nueva URL del escudo (puede ser `null`).
     * @param description Nueva descripción (puede ser `null`).
     */
    fun update(name: String, foundedYear: Int?, crestUrl: String?, description: String?) {
        this.name = name
        this.foundedYear = foundedYear
        this.crestUrl = crestUrl
        this.description = description
        this.updatedAt = LocalDateTime.now()
    }

    /**
     * Desactiva el club, marcándolo como inactivo en el sistema.
     */
    fun deactivate() {
        this.isActive = false
        this.updatedAt = LocalDateTime.now()
    }
}

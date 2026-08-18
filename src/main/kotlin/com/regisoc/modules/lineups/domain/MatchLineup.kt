package com.regisoc.modules.lineups.domain

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.matches.domain.Match
import com.regisoc.shared.domain.BaseEntity
import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OrderColumn
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime

/**
 * Entidad que representa la plantilla de un club para un partido.
 *
 * La plantilla está compuesta por hasta 22 jugadores, un entrenador y un
 * preparador físico opcional. Los datos de sus integrantes se guardan **por copia**
 * ([LineupPlayer], [LineupCoach], [LineupPhysicalTrainer]), de modo que la plantilla
 * refleja exactamente cómo se inscribieron, independientemente de los cambios
 * posteriores en las entidades originales.
 */
@Entity
@Table(
    name = "match_lineups",
    uniqueConstraints = [UniqueConstraint(name = "uk_match_lineup_club", columnNames = ["match_id", "club_id"])]
)
class MatchLineup(
    match: Match,
    club: Club
) : BaseEntity() {

    /**
     * @property match Partido al que pertenece la plantilla.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    var match: Match = match
        protected set

    /**
     * @property club Club al que representa la plantilla en ese partido.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    var club: Club = club
        protected set

    /**
     * @property players Lista de copias de los jugadores convocados (máximo 22).
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "match_lineup_players", joinColumns = [JoinColumn(name = "lineup_id")])
    @OrderColumn(name = "lineup_order")
    var players: MutableList<LineupPlayer> = mutableListOf()
        protected set

    /**
     * @property coach Copia de los datos del entrenador convocado.
     */
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "coachId", column = Column(name = "coach_id")),
        AttributeOverride(name = "firstName", column = Column(name = "coach_first_name")),
        AttributeOverride(name = "lastName", column = Column(name = "coach_last_name")),
        AttributeOverride(name = "documentNumber", column = Column(name = "coach_document_number")),
        AttributeOverride(name = "age", column = Column(name = "coach_age")),
        AttributeOverride(name = "dateOfBirth", column = Column(name = "coach_date_of_birth")),
        AttributeOverride(name = "photoUrl", column = Column(name = "coach_photo_url", columnDefinition = "text"))
    )
    var coach: LineupCoach? = null
        protected set

    /**
     * @property physicalTrainer Copia de los datos del preparador físico convocado (opcional).
     */
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "physicalTrainerId", column = Column(name = "physical_trainer_id")),
        AttributeOverride(name = "firstName", column = Column(name = "physical_trainer_first_name")),
        AttributeOverride(name = "lastName", column = Column(name = "physical_trainer_last_name")),
        AttributeOverride(name = "documentNumber", column = Column(name = "physical_trainer_document_number")),
        AttributeOverride(name = "age", column = Column(name = "physical_trainer_age")),
        AttributeOverride(name = "dateOfBirth", column = Column(name = "physical_trainer_date_of_birth")),
        AttributeOverride(name = "photoUrl", column = Column(name = "physical_trainer_photo_url", columnDefinition = "text"))
    )
    var physicalTrainer: LineupPhysicalTrainer? = null
        protected set

    /**
     * Reemplaza por completo la plantilla con las copias recibidas.
     *
     * @param players Copias de los jugadores convocados.
     * @param coach Copia del entrenador convocado.
     * @param physicalTrainer Copia del preparador físico convocado (puede ser `null`).
     */
    fun setLineup(
        players: List<LineupPlayer>,
        coach: LineupCoach?,
        physicalTrainer: LineupPhysicalTrainer?
    ) {
        this.players = players.toMutableList()
        this.coach = coach
        this.physicalTrainer = physicalTrainer
        this.updatedAt = LocalDateTime.now()
    }
}

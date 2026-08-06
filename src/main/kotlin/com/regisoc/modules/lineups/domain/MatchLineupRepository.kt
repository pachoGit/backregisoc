package com.regisoc.modules.lineups.domain

import java.util.Optional

/**
 * Repositorio para la entidad [MatchLineup].
 *
 * Define las operaciones de persistencia para las plantillas de los clubes en los partidos.
 */
interface MatchLineupRepository {

    /**
     * Guarda una plantilla en el repositorio.
     *
     * @param lineup Entidad [MatchLineup] a persistir.
     * @return La plantilla persistida.
     */
    fun save(lineup: MatchLineup): MatchLineup

    /**
     * Obtiene todas las plantillas de un partido específico.
     *
     * @param matchId Identificador del partido.
     * @return Lista de plantillas del partido (una por club).
     */
    fun findAllByMatchId(matchId: Long): List<MatchLineup>

    /**
     * Obtiene la plantilla de un club específico dentro de un partido.
     *
     * @param matchId Identificador del partido.
     * @param clubId Identificador del club.
     * @return Un [Optional] que contiene la plantilla si existe, o vacío si no se encuentra.
     */
    fun findByMatchIdAndClubId(matchId: Long, clubId: Long): Optional<MatchLineup>
}

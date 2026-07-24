package com.regisoc.modules.lineups.domain

import java.util.Optional

/**
 * Repositorio para la entidad [MatchLineup].
 *
 * Define las operaciones de persistencia para las alineaciones de jugadores en partidos,
 * incluyendo operaciones masivas y búsquedas por partido y club.
 */
interface MatchLineupRepository {

    /**
     * Guarda una alineación en el repositorio.
     *
     * @param lineup Entidad [MatchLineup] a persistir.
     * @return La alineación persistida.
     */
    fun save(lineup: MatchLineup): MatchLineup

    /**
     * Guarda múltiples alineaciones de forma masiva.
     *
     * @param lineups Lista de entidades [MatchLineup] a persistir.
     * @return La lista de alineaciones persistidas.
     */
    fun saveAll(lineups: List<MatchLineup>): List<MatchLineup>

    /**
     * Busca una alineación por su identificador único.
     *
     * @param id Identificador único de la alineación.
     * @return Un [Optional] que contiene la alineación si existe, o vacío si no se encuentra.
     */
    fun findById(id: Long): Optional<MatchLineup>

    /**
     * Obtiene todas las alineaciones de un partido específico.
     *
     * @param matchId Identificador del partido.
     * @return Lista de alineaciones del partido.
     */
    fun findAllByMatchId(matchId: Long): List<MatchLineup>

    /**
     * Obtiene las alineaciones de un club específico dentro de un partido.
     *
     * @param matchId Identificador del partido.
     * @param clubId Identificador del club.
     * @return Lista de alineaciones del club en ese partido.
     */
    fun findAllByMatchIdAndClubId(matchId: Long, clubId: Long): List<MatchLineup>

    /**
     * Elimina todas las alineaciones de un club en un partido específico.
     *
     * @param matchId Identificador del partido.
     * @param clubId Identificador del club.
     */
    fun deleteByMatchIdAndClubId(matchId: Long, clubId: Long)
}

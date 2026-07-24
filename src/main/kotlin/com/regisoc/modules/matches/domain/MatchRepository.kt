package com.regisoc.modules.matches.domain

import java.util.Optional

/**
 * Repositorio para la entidad [Match].
 *
 * Define las operaciones de persistencia disponibles para los partidos,
 * incluyendo búsquedas por jornada y por club participante.
 */
interface MatchRepository {

    /**
     * Guarda un partido en el repositorio.
     *
     * @param match Entidad [Match] a persistir.
     * @return El partido persistido.
     */
    fun save(match: Match): Match

    /**
     * Busca un partido por su identificador único.
     *
     * @param id Identificador único del partido.
     * @return Un [Optional] que contiene el partido si existe, o vacío si no se encuentra.
     */
    fun findById(id: String): Optional<Match>

    /**
     * Obtiene todos los partidos de una jornada específica.
     *
     * @param matchDateId Identificador de la jornada.
     * @return Lista de partidos de esa jornada.
     */
    fun findAllByMatchDateId(matchDateId: String): List<Match>

    /**
     * Obtiene todos los partidos en los que participa un club (ya sea como local o visitante).
     *
     * @param clubId Identificador del club.
     * @return Lista de partidos del club.
     */
    fun findByClubId(clubId: String): List<Match>

    /**
     * Elimina un partido del repositorio.
     *
     * @param match Entidad [Match] a eliminar.
     */
    fun delete(match: Match)
}

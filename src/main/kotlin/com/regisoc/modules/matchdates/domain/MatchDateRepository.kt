package com.regisoc.modules.matchdates.domain

import java.util.Optional

/**
 * Repositorio para la entidad [MatchDate].
 *
 * Define las operaciones de persistencia disponibles para las jornadas de un evento.
 */
interface MatchDateRepository {

    /**
     * Guarda una jornada en el repositorio.
     *
     * @param matchDate Entidad [MatchDate] a persistir.
     * @return La jornada persistida.
     */
    fun save(matchDate: MatchDate): MatchDate

    /**
     * Busca una jornada por su identificador único.
     *
     * @param id Identificador único de la jornada.
     * @return Un [Optional] que contiene la jornada si existe, o vacío si no se encuentra.
     */
    fun findById(id: String): Optional<MatchDate>

    /**
     * Obtiene todas las jornadas de un evento específico.
     *
     * @param eventId Identificador del evento.
     * @return Lista de jornadas asociadas al evento, ordenadas por fecha.
     */
    fun findAllByEventId(eventId: String): List<MatchDate>

    /**
     * Elimina una jornada del repositorio.
     *
     * @param matchDate Entidad [MatchDate] a eliminar.
     */
    fun delete(matchDate: MatchDate)
}

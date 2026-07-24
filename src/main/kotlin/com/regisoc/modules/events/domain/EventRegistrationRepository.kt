package com.regisoc.modules.events.domain

import java.util.Optional

/**
 * Repositorio para la entidad [EventRegistration].
 *
 * Define las operaciones de persistencia para las inscripciones de clubes a eventos,
 * incluyendo búsquedas por evento, por club o por la combinación de ambos.
 */
interface EventRegistrationRepository {

    /**
     * Guarda una inscripción en el repositorio.
     *
     * @param registration Entidad [EventRegistration] a persistir.
     * @return La inscripción persistida.
     */
    fun save(registration: EventRegistration): EventRegistration

    /**
     * Busca una inscripción por su identificador único.
     *
     * @param id Identificador único de la inscripción.
     * @return Un [Optional] que contiene la inscripción si existe, o vacío si no se encuentra.
     */
    fun findById(id: Long): Optional<EventRegistration>

    /**
     * Obtiene todas las inscripciones de un evento específico.
     *
     * @param eventId Identificador del evento.
     * @return Lista de inscripciones asociadas al evento.
     */
    fun findByEventId(eventId: Long): List<EventRegistration>

    /**
     * Busca la inscripción de un club específico en un evento específico.
     *
     * @param eventId Identificador del evento.
     * @param clubId Identificador del club.
     * @return Un [Optional] que contiene la inscripción si existe, o vacío si el club no está inscrito en ese evento.
     */
    fun findByEventIdAndClubId(eventId: Long, clubId: Long): Optional<EventRegistration>

    /**
     * Obtiene todas las inscripciones de un club (en todos los eventos en los que participa).
     *
     * @param clubId Identificador del club.
     * @return Lista de inscripciones del club.
     */
    fun findByClubId(clubId: Long): List<EventRegistration>

    /**
     * Elimina una inscripción del repositorio.
     *
     * @param registration Entidad [EventRegistration] a eliminar.
     */
    fun delete(registration: EventRegistration)
}

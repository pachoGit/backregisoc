package com.regisoc.modules.events.domain

import java.util.Optional

/**
 * Repositorio para la entidad [Event].
 *
 * Define las operaciones de persistencia disponibles para los eventos o torneos deportivos.
 */
interface EventRepository {

    /**
     * Guarda un evento en el repositorio.
     *
     * @param event Entidad [Event] a persistir.
     * @return El evento persistido.
     */
    fun save(event: Event): Event

    /**
     * Busca un evento por su identificador único.
     *
     * @param id Identificador único del evento.
     * @return Un [Optional] que contiene el evento si existe, o vacío si no se encuentra.
     */
    fun findById(id: Long): Optional<Event>

    /**
     * Obtiene todos los eventos registrados.
     *
     * @return Lista completa de eventos.
     */
    fun findAll(): List<Event>

    /**
     * Obtiene los eventos activos, es decir, no eliminados y en los que
     * al menos un club está registrado.
     *
     * @param clubId Identificador del club. Si se proporciona, solo se devuelven
     *               los eventos en los que ese club está registrado.
     * @return Lista de eventos activos.
     */
    fun findActive(clubId: Long?): List<Event>

    /**
     * Elimina un evento del repositorio.
     *
     * @param event Entidad [Event] a eliminar.
     */
    fun delete(event: Event)
}

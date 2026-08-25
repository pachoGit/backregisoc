package com.regisoc.modules.clubs.domain

import java.util.Optional

/**
 * Repositorio para la entidad [Club].
 *
 * Define las operaciones de persistencia disponibles para los clubes deportivos.
 */
interface ClubRepository {

    /**
     * Guarda un club en el repositorio (crea o actualiza según exista o no el identificador).
     *
     * @param club Entidad [Club] a persistir.
     * @return El club persistido con sus datos actualizados.
     */
    fun save(club: Club): Club

    /**
     * Busca un club por su identificador único.
     *
     * @param id Identificador único del club.
     * @return Un [Optional] que contiene el club si existe, o vacío si no se encuentra.
     */
    fun findById(id: Long): Optional<Club>

    /**
     * Busca un club activo por su identificador único.
     *
     * @param id Identificador único del club.
     * @return Un [Optional] que contiene el club si existe, o vacío si no se encuentra.
     */
    fun findActiveById(id: Long): Optional<Club>

    /**
     * Obtiene todos los clubes registrados en el sistema.
     *
     * @return Lista completa de clubes.
     */
    fun findAll(): List<Club>

    /**
     * Busca clubes cuyo nombre contenga la cadena especificada (búsqueda parcial e insensible a mayúsculas).
     *
     * @param name Texto a buscar dentro del nombre del club.
     * @return Lista de clubes cuyo nombre coincide parcialmente con la búsqueda.
     */
    fun findByNameContaining(name: String): List<Club>

    /**
     * Busca un club activo por su identificador único.
     *
     * @param clubId Identificador único del club.
     * @return Un [Optional] que contiene el club si existe, o vacío si no se encuentra.
     */
    fun findActiveByClubId(clubId: Long): Optional<Club>

    /**
     * Elimina un club del repositorio.
     *
     * @param club Entidad [Club] a eliminar.
     */
    fun delete(club: Club)
}

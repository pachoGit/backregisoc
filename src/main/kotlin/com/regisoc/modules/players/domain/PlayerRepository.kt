package com.regisoc.modules.players.domain

import java.util.Optional

/**
 * Repositorio para la entidad [Player].
 *
 * Define las operaciones de persistencia disponibles para los jugadores,
 * incluyendo búsquedas por club.
 */
interface PlayerRepository {

    /**
     * Guarda un jugador en el repositorio.
     *
     * @param player Entidad [Player] a persistir.
     * @return El jugador persistido.
     */
    fun save(player: Player): Player

    /**
     * Busca un jugador por su identificador único.
     *
     * @param id Identificador único del jugador.
     * @return Un [Optional] que contiene el jugador si existe, o vacío si no se encuentra.
     */
    fun findById(id: Long): Optional<Player>

    /**
     * Obtiene todos los jugadores de un club específico.
     *
     * @param clubId Identificador del club.
     * @return Lista de jugadores pertenecientes al club.
     */
    fun findAllByClubId(clubId: Long): List<Player>

    /**
     * Elimina un jugador del repositorio.
     *
     * @param player Entidad [Player] a eliminar.
     */
    fun delete(player: Player)
}

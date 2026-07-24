package com.regisoc.modules.players.domain

/**
 * Posiciones de juego que puede tener un jugador en el campo de fútbol.
 */
enum class PlayerPosition {

    /** Portero o guardameta. */
    GOALKEEPER,

    /** Defensa (central, lateral, etc.). */
    DEFENDER,

    /** Centrocampista o mediocampista. */
    MIDFIELDER,

    /** Delantero o atacante. */
    FORWARD
}

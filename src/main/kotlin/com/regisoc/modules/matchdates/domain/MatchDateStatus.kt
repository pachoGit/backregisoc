package com.regisoc.modules.matchdates.domain

/**
 * Estados que puede tener una jornada a lo largo de su ciclo de vida.
 */
enum class MatchDateStatus {

    /** La jornada está programada y aún no ha comenzado. */
    UPCOMING,

    /** La jornada se está disputando actualmente. */
    ONGOING,

    /** La jornada ha finalizado. */
    FINISHED,

    /** La jornada ha sido cancelada y no se disputará. */
    CANCELED
}

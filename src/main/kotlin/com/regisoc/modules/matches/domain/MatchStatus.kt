package com.regisoc.modules.matches.domain

/**
 * Estados que puede tener un partido a lo largo de su ciclo de vida.
 */
enum class MatchStatus {

    /** El partido está programado y aún no ha comenzado. */
    SCHEDULED,

    /** El partido se está disputando actualmente. */
    ONGOING,

    /** El partido ha finalizado con resultado definitivo. */
    FINISHED,

    /** El partido ha sido cancelado y no se disputará. */
    CANCELLED
}

package com.regisoc.modules.events.domain

/**
 * Estados que puede tener un evento o torneo a lo largo de su ciclo de vida.
 */
enum class EventStatus {

    /** El evento está programado y aún no ha comenzado. */
    UPCOMING,

    /** El evento se está disputando actualmente. */
    ONGOING,

    /** El evento ha finalizado. */
    FINISHED
}

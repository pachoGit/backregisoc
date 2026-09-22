package com.regisoc.modules.lineups.domain

/**
 * Estados que puede tener una planilla al momento de disputarse un partido.
 */
enum class LineupStatus {

    /**
     * @property OPEN Plantilla abierta a moficaciones.
     */
    OPEN,

    /**
     * @property CLOSE Plantilla cerrada a moficaciones.
     */
    CLOSE
}

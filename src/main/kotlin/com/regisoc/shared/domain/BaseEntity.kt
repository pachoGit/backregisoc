package com.regisoc.shared.domain

import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import java.time.LocalDateTime

/**
 * Entidad base abstracta que proporciona los campos comunes a todas las entidades del dominio.
 */
@MappedSuperclass
abstract class BaseEntity(

    /**
     * @property id Identificador único generado automáticamente para cada entidad.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    val id: Long = 0,

    /**
     * @property createdAt Fecha y hora de creación del registro; se asigna automáticamente al persistir por primera vez.
     */
    val createdAt: LocalDateTime = LocalDateTime.now(),

    /**
     * @property updatedAt Fecha y hora de la última modificación; se actualiza automáticamente cada vez que la entidad cambia.
     */
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    /**
     * @property deletedAt Fecha y hora de la eliminación.
     */
    var deletedAt: LocalDateTime? = null
)

package com.regisoc.shared.domain

import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime
import java.util.UUID

/**
 * Entidad base abstracta que proporciona los campos comunes a todas las entidades del dominio.
 *
 * @property id Identificador único universal (UUID) generado automáticamente para cada entidad.
 * @property createdAt Fecha y hora de creación del registro; se asigna automáticamente al persistir por primera vez.
 * @property updatedAt Fecha y hora de la última modificación; se actualiza automáticamente cada vez que la entidad cambia.
 * @property deletedAt Fecha y hora de la eliminación.
 */
@MappedSuperclass
abstract class BaseEntity(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var deletedAt: LocalDateTime? = null
)

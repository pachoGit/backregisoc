package com.regisoc.modules.clubs.infrastructure.web

import com.regisoc.modules.clubs.domain.Club
import java.time.LocalDateTime

data class ClubResponse(
    val id: Long,
    val name: String,
    val foundedYear: Int?,
    val crestUrl: String?,
    val description: String?,
    val createdBy: String,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(club: Club) = ClubResponse(
            id = club.id,
            name = club.name,
            foundedYear = club.foundedYear,
            crestUrl = club.crestUrl,
            description = club.description,
            createdBy = club.createdBy,
            isActive = club.isActive,
            createdAt = club.createdAt,
            updatedAt = club.updatedAt
        )
    }
}

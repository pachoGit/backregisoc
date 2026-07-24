package com.regisoc.modules.clubs.application

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.clubs.domain.ClubRepository
import org.springframework.stereotype.Service

data class CreateClubCommand(
    val name: String,
    val foundedYear: Int?,
    val crestUrl: String?,
    val description: String?,
    val createdBy: String
)

@Service
class CreateClubUseCase(private val repository: ClubRepository) {
    fun execute(command: CreateClubCommand): Club {
        val club = Club(
            name = command.name,
            foundedYear = command.foundedYear,
            crestUrl = command.crestUrl,
            description = command.description,
            createdBy = command.createdBy
        )
        return repository.save(club)
    }
}

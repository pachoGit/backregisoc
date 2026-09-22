package com.regisoc.modules.matchdates.application

import com.regisoc.modules.matchdates.domain.MatchDateRepository
import com.regisoc.modules.matchdates.domain.MatchDateStatus
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

data class StartMatchDateCommand(
    val matchDateId: Long
)

@Service
class StartMatchDateUseCase(
    private val repository: MatchDateRepository
) {
    fun execute(command: StartMatchDateCommand) {
        val matchDate = repository.findById(command.matchDateId)
            .orElseThrow { EntityNotFoundException("MatchDate not found with id: ${command.matchDateId}") }

        val current = matchDate.status
        require(current == MatchDateStatus.UPCOMING) {
            "Cannot start match date ${matchDate.id}: current status is $current"
        }

        matchDate.changeStatus(MatchDateStatus.ONGOING)
        repository.save(matchDate)
    }
}
package com.regisoc.modules.lineups.application

import com.regisoc.modules.lineups.domain.MatchLineupRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

data class CloseLineupCommand(
    val lineupId: Long
)

@Service
class CloseLineupUseCase(
    private val repository: MatchLineupRepository,
) {
    fun execute(command: CloseLineupCommand) {
        val matchLineup = repository.findById(command.lineupId).orElseThrow {
            EntityNotFoundException("Match not found: ${command.lineupId}")
        }

        matchLineup.close()

        repository.save(matchLineup)
    }
}

package com.regisoc.modules.matches.application

import com.regisoc.modules.matches.domain.Match
import com.regisoc.modules.matches.domain.MatchRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class GetMatchUseCase(private val repository: MatchRepository) {
    fun findById(id: Long): Match {
        return repository.findById(id)
            .orElseThrow { EntityNotFoundException("Match not found with id: $id") }
    }

    fun findByMatchDate(matchDateId: Long): List<Match> =
        repository.findAllByMatchDateId(matchDateId)

    fun findByClub(clubId: Long): List<Match> =
        repository.findByClubId(clubId)
}

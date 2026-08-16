package com.regisoc.modules.matches.infrastructure.persistence

import com.regisoc.modules.matches.domain.Match
import com.regisoc.modules.matches.domain.MatchRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class MatchRepositoryImpl(
    private val jpaRepository: MatchJpaRepository
) : MatchRepository {
    override fun save(match: Match): Match = jpaRepository.save(match)
    override fun findById(id: Long): Optional<Match> = jpaRepository.findById(id)
    override fun findAllByMatchDateId(matchDateId: Long): List<Match> = jpaRepository.findAllByMatchDateId(matchDateId)
    override fun findAllByEventId(eventId: Long): List<Match> = jpaRepository.findAllByEventId(eventId)
    override fun findByClubId(clubId: Long): List<Match> = jpaRepository.findByClubId(clubId)
    override fun delete(match: Match) = jpaRepository.delete(match)
    override fun findByEventIdAndClubId(eventId: Long, clubId: Long): List<Match>
        = jpaRepository.findByEventIdAndClubId(eventId, clubId)
}

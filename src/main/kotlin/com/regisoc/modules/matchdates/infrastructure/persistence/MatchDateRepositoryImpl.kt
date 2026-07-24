package com.regisoc.modules.matchdates.infrastructure.persistence

import com.regisoc.modules.matchdates.domain.MatchDate
import com.regisoc.modules.matchdates.domain.MatchDateRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class MatchDateRepositoryImpl(
    private val jpaRepository: MatchDateJpaRepository
) : MatchDateRepository {
    override fun save(matchDate: MatchDate): MatchDate = jpaRepository.save(matchDate)
    override fun findById(id: String): Optional<MatchDate> = jpaRepository.findById(id)
    override fun findAllByEventId(eventId: String): List<MatchDate> = jpaRepository.findAllByEventId(eventId)
    override fun delete(matchDate: MatchDate) = jpaRepository.delete(matchDate)
}

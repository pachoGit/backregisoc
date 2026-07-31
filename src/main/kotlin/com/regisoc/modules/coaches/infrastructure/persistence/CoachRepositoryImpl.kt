package com.regisoc.modules.coaches.infrastructure.persistence

import com.regisoc.modules.coaches.domain.Coach
import com.regisoc.modules.coaches.domain.CoachRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class CoachRepositoryImpl(
    private val jpaRepository: CoachJpaRepository
) : CoachRepository {
    override fun save(coach: Coach): Coach = jpaRepository.save(coach)
    override fun findById(id: Long): Optional<Coach> = jpaRepository.findById(id)
    override fun findAllByClubId(clubId: Long): List<Coach> = jpaRepository.findAllByClubId(clubId)
    override fun delete(coach: Coach) = jpaRepository.delete(coach)
}

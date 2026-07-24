package com.regisoc.modules.clubs.infrastructure.persistence

import com.regisoc.modules.clubs.domain.Club
import com.regisoc.modules.clubs.domain.ClubRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class ClubRepositoryImpl(
    private val jpaRepository: ClubJpaRepository
) : ClubRepository {
    override fun save(club: Club): Club = jpaRepository.save(club)
    override fun findById(id: Long): Optional<Club> = jpaRepository.findById(id)
    override fun findAll(): List<Club> = jpaRepository.findAll()
    override fun findByNameContaining(name: String): List<Club> = jpaRepository.findByNameContaining(name)
    override fun delete(club: Club) = jpaRepository.delete(club)
}

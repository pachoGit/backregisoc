package com.regisoc.modules.physicaltrainers.infrastructure.persistence

import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainer
import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainerRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class PhysicalTrainerRepositoryImpl(
    private val jpaRepository: PhysicalTrainerJpaRepository
) : PhysicalTrainerRepository {
    override fun save(physicalTrainer: PhysicalTrainer): PhysicalTrainer = jpaRepository.save(physicalTrainer)
    override fun findById(id: Long): Optional<PhysicalTrainer> = jpaRepository.findById(id)
    override fun findAllByClubId(clubId: Long): List<PhysicalTrainer> = jpaRepository.findAllByClubId(clubId)
    override fun delete(physicalTrainer: PhysicalTrainer) = jpaRepository.delete(physicalTrainer)
}

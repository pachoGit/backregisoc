package com.regisoc.modules.physicaltrainers.infrastructure.persistence

import com.regisoc.modules.physicaltrainers.domain.PhysicalTrainer
import org.springframework.data.jpa.repository.JpaRepository

interface PhysicalTrainerJpaRepository : JpaRepository<PhysicalTrainer, Long> {
    fun findAllByClubId(clubId: Long): List<PhysicalTrainer>
}

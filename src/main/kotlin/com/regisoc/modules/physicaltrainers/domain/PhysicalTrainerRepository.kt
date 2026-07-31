package com.regisoc.modules.physicaltrainers.domain

import java.util.Optional

interface PhysicalTrainerRepository {
    fun save(physicalTrainer: PhysicalTrainer): PhysicalTrainer
    fun findById(id: Long): Optional<PhysicalTrainer>
    fun findAllByClubId(clubId: Long): List<PhysicalTrainer>
    fun delete(physicalTrainer: PhysicalTrainer)
}

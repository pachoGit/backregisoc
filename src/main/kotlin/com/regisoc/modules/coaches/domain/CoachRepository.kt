package com.regisoc.modules.coaches.domain

import java.util.Optional

interface CoachRepository {
    fun save(coach: Coach): Coach
    fun findById(id: Long): Optional<Coach>
    fun findAllByClubId(clubId: Long): List<Coach>
    fun delete(coach: Coach)
}

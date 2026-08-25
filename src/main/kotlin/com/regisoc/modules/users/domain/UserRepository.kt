package com.regisoc.modules.users.domain

import java.util.Optional

interface UserRepository {
    fun save(user: User): User
    fun findById(id: Long): Optional<User>
    fun findByUsername(username: String): Optional<User>
    fun existsByUsername(username: String): Boolean
    fun findAll(): List<User>
    fun delete(user: User)
}

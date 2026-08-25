package com.regisoc.modules.users.infrastructure.persistence

import com.regisoc.modules.users.domain.User
import com.regisoc.modules.users.domain.UserRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class UserRepositoryImpl(
    private val jpaRepository: UserJpaRepository
) : UserRepository {
    override fun save(user: User): User = jpaRepository.save(user)
    override fun findById(id: Long): Optional<User> = jpaRepository.findById(id)
    override fun findByUsername(username: String): Optional<User> = jpaRepository.findByUsername(username)
    override fun existsByUsername(username: String): Boolean = jpaRepository.existsByUsername(username)
    override fun findAll(): List<User> = jpaRepository.findAll()
    override fun delete(user: User) = jpaRepository.delete(user)
}

package com.regisoc.modules.users.application

import com.regisoc.modules.users.domain.UserRepository
import org.springframework.stereotype.Service

@Service
class GetUserUseCase(
    private val userRepository: UserRepository
) {
    fun findById(id: Long): com.regisoc.modules.users.domain.User {
        return userRepository.findById(id)
            .orElseThrow { jakarta.persistence.EntityNotFoundException("User not found with id: $id") }
    }

    fun findAll(): List<com.regisoc.modules.users.domain.User> = userRepository.findAll()
}

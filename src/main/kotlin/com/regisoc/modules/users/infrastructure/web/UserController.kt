package com.regisoc.modules.users.infrastructure.web

import com.regisoc.modules.users.application.CreateUserCommand
import com.regisoc.modules.users.application.CreateUserUseCase
import com.regisoc.modules.users.application.GetUserUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserUseCase: GetUserUseCase
) {
    @PostMapping
    fun create(@Valid @RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        val user = createUserUseCase.execute(
            CreateUserCommand(
                name = request.name,
                surname = request.surname,
                documentNumber = request.documentNumber,
                username = request.username,
                password = request.password,
                role = request.role,
                clubId = request.clubId
            )
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        val user = getUserUseCase.findById(id)
        return ResponseEntity.ok(UserResponse.from(user))
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<UserResponse>> {
        val users = getUserUseCase.findAll()
        return ResponseEntity.ok(users.map { UserResponse.from(it) })
    }
}

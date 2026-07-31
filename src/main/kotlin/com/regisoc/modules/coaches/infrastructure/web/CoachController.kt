package com.regisoc.modules.coaches.infrastructure.web

import com.regisoc.modules.coaches.application.CreateCoachCommand
import com.regisoc.modules.coaches.application.CreateCoachUseCase
import com.regisoc.modules.coaches.application.DeleteCoachUseCase
import com.regisoc.modules.coaches.application.GetCoachUseCase
import com.regisoc.modules.coaches.application.UpdateCoachCommand
import com.regisoc.modules.coaches.application.UpdateCoachUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/coaches")
class CoachController(
    private val createCoachUseCase: CreateCoachUseCase,
    private val updateCoachUseCase: UpdateCoachUseCase,
    private val getCoachUseCase: GetCoachUseCase,
    private val deleteCoachUseCase: DeleteCoachUseCase
) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(
        @RequestBody @Valid request: CreateCoachRequest
    ): ResponseEntity<CoachResponse> {
        val command = CreateCoachCommand(
            clubId = request.clubId,
            firstName = request.firstName,
            lastName = request.lastName,
            documentNumber = request.documentNumber,
            age = request.age,
            dateOfBirth = request.dateOfBirth,
            photoUrl = request.photoUrl
        )
        val coach = createCoachUseCase.execute(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(CoachResponse.from(coach))
    }

    @PutMapping("/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid request: UpdateCoachRequest
    ): ResponseEntity<Void> {
        val command = UpdateCoachCommand(
            id = id,
            firstName = request.firstName,
            lastName = request.lastName,
            documentNumber = request.documentNumber,
            age = request.age,
            dateOfBirth = request.dateOfBirth,
            photoUrl = request.photoUrl
        )
        updateCoachUseCase.execute(command)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<CoachResponse> {
        val coach = getCoachUseCase.findById(id)
        return ResponseEntity.ok(CoachResponse.from(coach))
    }

    @GetMapping
    fun getByClub(@RequestParam clubId: Long): ResponseEntity<List<CoachResponse>> {
        val coaches = getCoachUseCase.findByClub(clubId)
        return ResponseEntity.ok(coaches.map { CoachResponse.from(it) })
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        deleteCoachUseCase.execute(id)
        return ResponseEntity.noContent().build()
    }
}

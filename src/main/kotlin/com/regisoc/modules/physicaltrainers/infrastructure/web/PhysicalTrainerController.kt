package com.regisoc.modules.physicaltrainers.infrastructure.web

import com.regisoc.modules.physicaltrainers.application.CreatePhysicalTrainerCommand
import com.regisoc.modules.physicaltrainers.application.CreatePhysicalTrainerUseCase
import com.regisoc.modules.physicaltrainers.application.DeletePhysicalTrainerUseCase
import com.regisoc.modules.physicaltrainers.application.GetPhysicalTrainerUseCase
import com.regisoc.modules.physicaltrainers.application.UpdatePhysicalTrainerCommand
import com.regisoc.modules.physicaltrainers.application.UpdatePhysicalTrainerUseCase
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
@RequestMapping("/api/physical-trainers")
class PhysicalTrainerController(
    private val createPhysicalTrainerUseCase: CreatePhysicalTrainerUseCase,
    private val updatePhysicalTrainerUseCase: UpdatePhysicalTrainerUseCase,
    private val getPhysicalTrainerUseCase: GetPhysicalTrainerUseCase,
    private val deletePhysicalTrainerUseCase: DeletePhysicalTrainerUseCase
) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(
        @RequestBody @Valid request: CreatePhysicalTrainerRequest
    ): ResponseEntity<PhysicalTrainerResponse> {
        val command = CreatePhysicalTrainerCommand(
            clubId = request.clubId,
            firstName = request.firstName,
            lastName = request.lastName,
            documentNumber = request.documentNumber,
            age = request.age,
            dateOfBirth = request.dateOfBirth,
            photoUrl = request.photoUrl,
            documentFrontUrl = request.documentFrontUrl,
            documentBackUrl = request.documentBackUrl
        )
        val physicalTrainer = createPhysicalTrainerUseCase.execute(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(PhysicalTrainerResponse.from(physicalTrainer))
    }

    @PutMapping("/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid request: UpdatePhysicalTrainerRequest
    ): ResponseEntity<Void> {
        val command = UpdatePhysicalTrainerCommand(
            id = id,
            firstName = request.firstName,
            lastName = request.lastName,
            documentNumber = request.documentNumber,
            age = request.age,
            dateOfBirth = request.dateOfBirth,
            photoUrl = request.photoUrl,
            documentFrontUrl = request.documentFrontUrl,
            documentBackUrl = request.documentBackUrl
        )
        updatePhysicalTrainerUseCase.execute(command)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<PhysicalTrainerResponse> {
        val physicalTrainer = getPhysicalTrainerUseCase.findById(id)
        return ResponseEntity.ok(PhysicalTrainerResponse.from(physicalTrainer))
    }

    @GetMapping
    fun getByClub(@RequestParam clubId: Long): ResponseEntity<List<PhysicalTrainerResponse>> {
        val physicalTrainers = getPhysicalTrainerUseCase.findByClub(clubId)
        return ResponseEntity.ok(physicalTrainers.map { PhysicalTrainerResponse.from(it) })
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        deletePhysicalTrainerUseCase.execute(id)
        return ResponseEntity.noContent().build()
    }
}

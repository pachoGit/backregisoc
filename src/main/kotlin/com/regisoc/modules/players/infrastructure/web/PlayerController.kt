package com.regisoc.modules.players.infrastructure.web

import com.regisoc.modules.players.application.*
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/players")
class PlayerController(
    private val registerPlayerUseCase: RegisterPlayerUseCase,
    private val updatePlayerUseCase: UpdatePlayerUseCase,
    private val getPlayerUseCase: GetPlayerUseCase,
    private val deletePlayerUseCase: DeletePlayerUseCase,
    private val uploadPlayerPhotosUseCase: UploadPlayerPhotosUseCase
) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(
        @RequestBody @Valid request: CreatePlayerRequest
    ): ResponseEntity<PlayerResponse> {
        val command = RegisterPlayerCommand(
            clubId = request.clubId,
            firstName = request.firstName,
            lastName = request.lastName,
            documentNumber = request.documentNumber,
            age = request.age,
            dateOfBirth = request.dateOfBirth,
            position = request.position,
            photoUrl = request.photoUrl,
            documentFrontUrl = request.documentFrontUrl,
            documentBackUrl = request.documentBackUrl
        )
        val player = registerPlayerUseCase.execute(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(PlayerResponse.from(player))
    }

    @PutMapping("/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid request: UpdatePlayerRequest
    ): ResponseEntity<Void> {
        val command = UpdatePlayerCommand(
            id = id,
            firstName = request.firstName,
            lastName = request.lastName,
            documentNumber = request.documentNumber,
            age = request.age,
            dateOfBirth = request.dateOfBirth,
            position = request.position,
            photoUrl = request.photoUrl,
            documentFrontUrl = request.documentFrontUrl,
            documentBackUrl = request.documentBackUrl
        )
        updatePlayerUseCase.execute(command)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/photos", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadPhotos(
        @PathVariable id: Long,
        @RequestPart("photo", required = false) photo: MultipartFile?,
        @RequestPart("documentFront", required = false) documentFront: MultipartFile?,
        @RequestPart("documentBack", required = false) documentBack: MultipartFile?
    ): ResponseEntity<Void> {
        val command = UploadPlayerPhotosCommand(
            playerId = id,
            photo = photo,
            documentFront = documentFront,
            documentBack = documentBack
        )
        uploadPlayerPhotosUseCase.execute(command)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<PlayerResponse> {
        val player = getPlayerUseCase.findById(id)
        return ResponseEntity.ok(PlayerResponse.from(player))
    }

    @GetMapping
    fun getByClub(@RequestParam clubId: Long): ResponseEntity<List<PlayerResponse>> {
        val players = getPlayerUseCase.findByClub(clubId)
        return ResponseEntity.ok(players.map { PlayerResponse.from(it) })
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        deletePlayerUseCase.execute(id)
        return ResponseEntity.noContent().build()
    }
}

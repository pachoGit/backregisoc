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
    private val deletePlayerUseCase: DeletePlayerUseCase
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestPart("request") @Valid request: CreatePlayerRequest,
        @RequestPart("photo", required = false) photo: MultipartFile?,
        @RequestPart("documentFront", required = false) documentFront: MultipartFile?,
        @RequestPart("documentBack", required = false) documentBack: MultipartFile?
    ): ResponseEntity<PlayerResponse> {
        val command = RegisterPlayerCommand(
            clubId = request.clubId,
            name = request.name,
            dateOfBirth = request.dateOfBirth,
            position = request.position,
            photo = photo,
            documentFront = documentFront,
            documentBack = documentBack
        )
        val player = registerPlayerUseCase.execute(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(PlayerResponse.from(player))
    }

    @PutMapping("/{id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun update(
        @PathVariable id: String,
        @RequestPart("request") @Valid request: UpdatePlayerRequest,
        @RequestPart("photo", required = false) photo: MultipartFile?,
        @RequestPart("documentFront", required = false) documentFront: MultipartFile?,
        @RequestPart("documentBack", required = false) documentBack: MultipartFile?
    ): ResponseEntity<Void> {
        val command = UpdatePlayerCommand(
            id = id,
            name = request.name,
            dateOfBirth = request.dateOfBirth,
            position = request.position,
            photo = photo,
            documentFront = documentFront,
            documentBack = documentBack
        )
        updatePlayerUseCase.execute(command)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<PlayerResponse> {
        val player = getPlayerUseCase.findById(id)
        return ResponseEntity.ok(PlayerResponse.from(player))
    }

    @GetMapping
    fun getByClub(@RequestParam clubId: String): ResponseEntity<List<PlayerResponse>> {
        val players = getPlayerUseCase.findByClub(clubId)
        return ResponseEntity.ok(players.map { PlayerResponse.from(it) })
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        deletePlayerUseCase.execute(id)
        return ResponseEntity.noContent().build()
    }
}

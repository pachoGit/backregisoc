package com.regisoc.modules.clubs.infrastructure.web

import com.regisoc.modules.clubs.application.*
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/clubs")
class ClubController(
    private val createClubUseCase: CreateClubUseCase,
    private val updateClubUseCase: UpdateClubUseCase,
    private val getClubUseCase: GetClubUseCase,
    private val deleteClubUseCase: DeleteClubUseCase
) {
    @PostMapping
    fun create(@Valid @RequestBody request: CreateClubRequest): ResponseEntity<ClubResponse> {
        val command = CreateClubCommand(
            name = request.name,
            foundedYear = request.foundedYear,
            crestUrl = request.crestUrl,
            description = request.description,
            createdBy = request.createdBy
        )
        val club = createClubUseCase.execute(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(ClubResponse.from(club))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @Valid @RequestBody request: UpdateClubRequest): ResponseEntity<Void> {
        val command = UpdateClubCommand(
            id = id,
            name = request.name,
            foundedYear = request.foundedYear,
            crestUrl = request.crestUrl,
            description = request.description
        )
        updateClubUseCase.execute(command)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<ClubResponse> {
        val club = getClubUseCase.findById(id)
        return ResponseEntity.ok(ClubResponse.from(club))
    }

    @GetMapping
    fun getAll(@RequestParam(required = false) name: String?): ResponseEntity<List<ClubResponse>> {
        val clubs = if (name != null) getClubUseCase.searchByName(name) else getClubUseCase.findAll()
        return ResponseEntity.ok(clubs.map { ClubResponse.from(it) })
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        deleteClubUseCase.execute(id)
        return ResponseEntity.noContent().build()
    }
}

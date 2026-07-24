package com.regisoc.modules.events.application

import com.regisoc.modules.events.domain.Event
import com.regisoc.modules.events.domain.EventRepository
import com.regisoc.modules.events.domain.EventStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CreateEventUseCaseTest {

    @Test
    fun `should create event with upcoming status`() {
        val repository = mockk<EventRepository>()
        val useCase = CreateEventUseCase(repository)

        val command = CreateEventCommand(
            name = "Torneo Test",
            description = "Desc",
            startDate = LocalDate.now(),
            endDate = LocalDate.now().plusMonths(1)
        )

        every { repository.save(any()) } answers { firstArg() }

        val result = useCase.execute(command)

        assertEquals("Torneo Test", result.name)
        assertEquals(EventStatus.UPCOMING, result.status)
        verify { repository.save(any()) }
    }
}

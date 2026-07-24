package com.regisoc.shared.infrastructure

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.net.URI

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFound(ex: EntityNotFoundException): ProblemDetail {
        val problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        problem.title = "Not Found"
        problem.detail = ex.message ?: "Resource not found"
        problem.type = URI.create("about:blank")
        return problem
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadArgument(ex: IllegalArgumentException): ProblemDetail {
        val problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        problem.title = "Bad Request"
        problem.detail = ex.message ?: "Invalid argument"
        problem.type = URI.create("about:blank")
        return problem
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleConflict(ex: IllegalStateException): ProblemDetail {
        val problem = ProblemDetail.forStatus(HttpStatus.CONFLICT)
        problem.title = "Conflict"
        problem.detail = ex.message ?: "State conflict"
        problem.type = URI.create("about:blank")
        return problem
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ProblemDetail {
        val errors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid value")
        }
        val problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        problem.title = "Validation Failed"
        problem.detail = "One or more fields are invalid"
        problem.setProperty("errors", errors)
        return problem
    }
}

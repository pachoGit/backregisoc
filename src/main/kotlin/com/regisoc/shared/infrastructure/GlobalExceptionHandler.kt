package com.regisoc.shared.infrastructure

import jakarta.persistence.EntityNotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
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

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(ex: AccessDeniedException): ProblemDetail {
        val problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN)
        problem.title = "Forbidden"
        problem.detail = ex.message ?: "Access denied"
        problem.type = URI.create("about:blank")
        return problem
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(ex: BadCredentialsException): ProblemDetail {
        val problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED)
        problem.title = "Unauthorized"
        problem.detail = "Invalid username or password"
        problem.type = URI.create("about:blank")
        return problem
    }
}

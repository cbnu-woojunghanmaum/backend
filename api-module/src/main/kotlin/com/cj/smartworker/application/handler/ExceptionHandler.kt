package com.cj.smartworker.application.handler

import com.cj.smartworker.application.response.GenericResponse
import com.cj.smartworker.domain.member.exception.MemberDomainException
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.Exception
import java.lang.IllegalStateException

@RestControllerAdvice
internal class ExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(exception: RuntimeException): GenericResponse<Unit> {
        return GenericResponse(
            data = Unit,
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            messages = listOf(exception.message),
            success = false,
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentExceptionHandler(exception: IllegalArgumentException): GenericResponse<Unit> {
        return GenericResponse(
            data = Unit,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            messages = listOf(exception.message),
            success = false,
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException::class)
    fun illegalStateExceptionHandler(exception: IllegalStateException): GenericResponse<Unit> {
        return GenericResponse(
            data = Unit,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            messages = listOf(exception.message),
            success = false,
        )
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun exceptionHandler(exception: Exception): GenericResponse<Unit> {
        return GenericResponse(
            data = Unit,
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            messages = listOf(exception.message),
            success = false,
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberDomainException::class)
    fun membershipDomainExceptionHandler(exception: MemberDomainException): GenericResponse<Unit> {
        return GenericResponse(
            data = Unit,
            statusCode = HttpStatus.BAD_REQUEST.value(),
            messages = listOf(exception.message),
            success = false,
        )
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException::class)
    fun accessDeniedExceptionHandler(exception: AccessDeniedException): GenericResponse<Unit> {
        return GenericResponse(
            data = Unit,
            statusCode = HttpStatus.UNAUTHORIZED.value(),
            messages = listOf(exception.message),
            success = false,
        )
    }
}

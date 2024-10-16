package com.cj.smartworker.security.handler

import com.cj.smartworker.application.response.GenericResponse
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtEntryPointHandler(
    private val objectMapper: ObjectMapper,
): AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.let {
            val genericResponse = GenericResponse(
                data = Unit,
                statusCode = HttpStatus.UNAUTHORIZED.value(),
                messages = listOf("401 Error UNAUTHORIZED"),
                success = false,
            )
            val stringValue = objectMapper.writeValueAsString(genericResponse)
            response.status = HttpStatus.UNAUTHORIZED.value()
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            val writer = response.writer
            writer.write(stringValue)
            writer.flush()
        }
    }
}

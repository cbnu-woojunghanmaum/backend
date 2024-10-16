package com.cj.smartworker.security.handler

import com.cj.smartworker.application.response.GenericResponse
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class JwtAccessDeniedHandler(
    private val objectMapper: ObjectMapper,
): AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response?.let {
            val genericResponse = GenericResponse(
                data = Unit,
                statusCode = HttpStatus.FORBIDDEN.value(),
                messages = listOf("403 Error FORBIDDEN"),
                success = false
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

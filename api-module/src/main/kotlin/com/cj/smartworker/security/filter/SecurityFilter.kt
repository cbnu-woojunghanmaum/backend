package com.cj.smartworker.security.filter

import com.cj.smartworker.domain.util.logger
import com.cj.smartworker.security.MemberContext.MEMBER
import com.cj.smartworker.security.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

internal class SecurityFilter(
    private val tokenProvider: TokenProvider,
): OncePerRequestFilter() {
    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val REFRESH_TOKEN = "Refresh"
        const val IS_REFRESH = "IsRefresh"
    }
    private val log = logger()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt: String? = resolveToken(request)
        val requestURI = request.requestURI
        log.info("request uri: $requestURI")

        // 유효성 검증
        if (org.springframework.util.StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            createAuthFromAccessToken(
                httpServletResponse = response,
                jwt = jwt!!,
                isRefresh = stringToBoolean(request.getHeader(IS_REFRESH))
            )
        }
        filterChain.doFilter(request, response)
    }

    private fun stringToBoolean(isRefresh: String?): Boolean {
        return isRefresh != null && isRefresh == "true"
    }

    private fun createAuthFromAccessToken(
        httpServletResponse: HttpServletResponse,
        jwt: String,
        isRefresh: Boolean,
    ) {
        val authentication = tokenProvider.authenticate(jwt)
        val member = MEMBER
        SecurityContextHolder.getContext().authentication = authentication
        val token = tokenProvider.createToken(authentication, member)
        httpServletResponse.setHeader(AUTHORIZATION_HEADER, token)

        // refresh token인 경우 refresh token 재발행
        if (isRefresh) {
            log.info("create refresh token")
            val refreshToken = tokenProvider.createRefreshToken(authentication, member)
            httpServletResponse.setHeader(REFRESH_TOKEN, refreshToken)
        }
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }
}

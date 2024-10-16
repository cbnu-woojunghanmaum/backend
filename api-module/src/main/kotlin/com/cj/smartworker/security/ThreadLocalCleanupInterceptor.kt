package com.cj.smartworker.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

@Component
internal class ThreadLocalCleanupInterceptor: HandlerInterceptor {

    /**
     * http call이 끝난 후 ThreadLocal을 초기화한다.
     */
    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        MemberContext.clear()
    }
}

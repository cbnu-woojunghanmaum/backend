package com.cj.smartworker.security.configuration

import com.cj.smartworker.domain.member.valueobject.Authority
import com.cj.smartworker.security.TokenProvider
import com.cj.smartworker.security.filter.SecurityFilter
import com.cj.smartworker.security.handler.JwtAccessDeniedHandler
import com.cj.smartworker.security.handler.JwtEntryPointHandler
import com.cj.smartworker.security.service.CustomUserDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(
    private val tokenProvider: TokenProvider,
    private val customUserDetailService: CustomUserDetailService,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val jwtEntryPointHandler: JwtEntryPointHandler,
) {

    companion object {
        val publicUrlArray = arrayOf(
            "/",
            "/api/v1/member/signup",
            "/api/v1/member/login",
            "/api/v1/member/check/login-id",
            "/api/v1/member/send/approval-code",
            "/api/v1/member/approve",
        )
        val swaggerUrlArray = arrayOf(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
        )
    }

    @Bean
    fun filterChain(http: HttpSecurity): DefaultSecurityFilterChain? {
        http.csrf { it.disable() }
            .cors{ it.configurationSource(corsConfigurationSource()) }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(*publicUrlArray).permitAll()
                    .requestMatchers(*swaggerUrlArray).permitAll()
                    .requestMatchers(GET, "/actuator/**").permitAll()
                    .anyRequest().fullyAuthenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(
                SecurityFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter::class.java,
            ).exceptionHandling {
                it.accessDeniedHandler(jwtAccessDeniedHandler)
                it.authenticationEntryPoint(jwtEntryPointHandler)
            }
            .userDetailsService(customUserDetailService)
            .authenticationProvider(tokenProvider)
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource = let {
        val config = CorsConfiguration()
        config.allowedOriginPatterns = listOf("*")
        config.allowedHeaders = listOf("*")
        config.allowedHeaders = listOf(
            SecurityFilter.AUTHORIZATION_HEADER,
            SecurityFilter.IS_REFRESH,
            SecurityFilter.REFRESH_TOKEN,
            "Content-Type",
        )
        config.allowedMethods = listOf("*")
        config.allowedOrigins = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        source
    }

    // default grant prefix: ROLE_
    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults {
        return GrantedAuthorityDefaults("ROLE_")
    }

    @Bean
    fun roleHierarchy(): RoleHierarchy {
        return RoleHierarchyImpl.withDefaultRolePrefix()
            .role(Authority.ADMIN.name).implies(Authority.EMPLOYEE.name)
            .build()
    }
}

package com.cj.smartworker.security

import com.cj.smartworker.business.member.port.out.FindMemberPort
import com.cj.smartworker.domain.member.entity.Member
import com.cj.smartworker.domain.member.exception.MemberDomainException
import com.cj.smartworker.domain.member.valueobject.MemberId
import com.cj.smartworker.domain.util.logger
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class TokenProvider(
    @Value("\${jwt.secret}")
    private val secret: String,
    private val findMemberPort: FindMemberPort,
) : AuthenticationProvider {
    private val logger = logger()

    companion object {
        const val AUTHORITIES_KEY = "auth"
    }

    val key: Key = Keys.hmacShaKeyFor(
        Base64.getDecoder().decode(secret)
    )
    private val ONE_MINUTE: Long = 1000 * 60 // 1minutes

    fun createToken(member: Member): String = let {
        val authentication = authenticate(member)
        SecurityContextHolder.getContext().authentication = authentication
        createToken(authentication, member)
    }

    fun createToken(authentication: Authentication, member: Member): String {
        val authorities: String = authentication.authorities
            .joinToString(",") { it.authority }

        val now: Long = Date().time
        // 임시 수정
//        val validity = Date(now.plus(ONE_MINUTE * 15))
        val validity = Date(now.plus(ONE_MINUTE * 60 * 4)) // 4H

        return Jwts.builder()
            .header()
            .add(
                mapOf(
                    "Name" to member.employeeName.employeeName,
                    "heartRateThreshold" to member.heartRateThreshold.value,
                )
            )
            .and()
            .subject(authentication.name)
            .claim(AUTHORITIES_KEY, authorities)
            .issuedAt(Date(System.currentTimeMillis()))
            // SHA512로 암호화
            .signWith(key)
            .expiration(validity)
            .compact()
    }

    fun createRefreshToken(member: Member): String = let {
        logger.info("createRefreshToken from membership")
        val authentication = authenticate(member)
        createRefreshToken(authentication, member)
    }

    fun createRefreshToken(authentication: Authentication, member: Member): String {
        val authorities: String = authentication.authorities
            .joinToString(",") { it.authority }

        // 14 days refresh token
        val now: Long = Date().time
        val validity = Date(now.plus(ONE_MINUTE * 60 * 24 * 14))

        return Jwts.builder()
            .header()
            .add(
                mapOf(
                    "Name" to member.employeeName.employeeName,
                    "heartRateThreshold" to member.heartRateThreshold.value,
                )
            )
            .and()
            .subject(authentication.name)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key)
            .expiration(validity)
            .compact()
    }

    fun authenticate(member: Member): Authentication {
        val authorities = member.authorities.map { SimpleGrantedAuthority(it.authority.role) }.toSet()
        val principal = User(
            member.memberId?.id.toString(),
            member.employeeName.employeeName,
            authorities
        )
        return UsernamePasswordAuthenticationToken(principal, member.employeeName.employeeName, authorities)
    }

    override fun authenticate(authentication: Authentication?): Authentication {
        return authentication?.let {
            UsernamePasswordAuthenticationToken(
                authentication.principal,
                authentication.credentials,
                authentication.authorities,
            )
        } ?: run {
            throw IllegalArgumentException("authentication is null")
        }
    }

    fun authenticate(token: String): Authentication {
        val claims = Jwts
            .parser()
            .setSigningKey(key)
            .build()
            .parseSignedClaims(token)
            .payload

        claims[AUTHORITIES_KEY].toString().split(",")
            .map { SimpleGrantedAuthority(it) }
            .toList()
            .let { authorities ->
                val member = findMemberPort.findById(MemberId(claims.subject.toLong()))
                    ?: run {
                        throw MemberDomainException("멤버를 찾을 수 없습니다")
                    }
                val principal = User(claims.subject, member.employeeName.employeeName, authorities)
                MemberContext.MEMBER = member
                return UsernamePasswordAuthenticationToken(principal, member.employeeName.employeeName, authorities)
            }
    }

    fun validateToken(token: String?): Boolean {

        if (token == null) {
            logger.info("token is null")
            return false
        }

        try {
            val build = Jwts.parser().setSigningKey(key).build()
            build.parseSignedClaims(token)
            return true
        } catch (e: SecurityException) {
            logger.info("잘못된 JWT 서명입니다", e)
        } catch (e: MalformedJwtException) {
            logger.info("잘못된 JWT 서명입니다", e)
        } catch (e: ExpiredJwtException) {
            logger.info("만료된 JWT 서명입니다", e)
        } catch (e: UnsupportedJwtException) {
            logger.error("지원하지 않는 JWT 토큰입니다", e)
        } catch (e: IllegalFormatException) {
            logger.info("JWT 토큰이 잘못되었습니다", e)
        }
        return false
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

}

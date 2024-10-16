package com.cj.smartworker.business.member.service

import com.cj.smartworker.business.fcm.port.out.FindTokenPort
import com.cj.smartworker.business.member.dto.TokenResponse
import com.cj.smartworker.business.member.dto.request.LoginRequest
import com.cj.smartworker.business.member.port.`in`.LoginUseCase
import com.cj.smartworker.business.member.port.out.FindMemberPort
import com.cj.smartworker.business.fcm.port.out.SaveTokenPort
import com.cj.smartworker.domain.fcm.entity.DeviceToken
import com.cj.smartworker.domain.member.exception.MemberDomainException
import com.cj.smartworker.domain.member.valueobject.LoginId
import com.cj.smartworker.domain.fcm.valueobject.Token
import com.cj.smartworker.domain.util.PasswordEncodeUtil
import com.cj.smartworker.domain.util.logger
import com.cj.smartworker.security.TokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class LoginService(
    private val findMemberPort: FindMemberPort,
    private val tokenProvider: TokenProvider,
    private val findTokenPort: FindTokenPort,
    private val saveTokenPort: SaveTokenPort,
): LoginUseCase {
    private val logger = logger()

    @Transactional
    override fun login(loginRequest: LoginRequest): TokenResponse {
        val member = findMemberPort.findByLoginId(LoginId(loginRequest.loginId)) ?: run {
            throw MemberDomainException("멤버십을 찾지 못하였습니다")
        }
        val matches = PasswordEncodeUtil.matches(loginRequest.password, member.password.password)
        if (matches.not()) {
            throw MemberDomainException("패스워드가 일치하지 않습니다")
        }

        findTokenPort.findByMemberId(member.memberId!!) ?.let {
            if (loginRequest.token != null &&  it.token.token != loginRequest.token) {
                logger.info("기존 디바이스 토큰이 존재하고, 새로운 디바이스 토큰이 다릅니다. 기존 디바이스 토큰을 업데이트 합니다.")
                it.updateToken(Token(loginRequest.token))
                saveTokenPort.saveToken(it)
            }
        } ?: run {
            logger.info("디바이스 토큰이 존재하지 않습니다.")
            if(loginRequest.token != null) {
                logger.info("새로운 디바이스 토큰을 저장합니다.")
                saveTokenPort.saveToken(
                    DeviceToken(
                        _deviceTokenId = null,
                        _member = member,
                        _token = Token(loginRequest.token),
                    )
                )
            }
        }

        return TokenResponse(
            token = tokenProvider.createToken(member),
            refreshToken = tokenProvider.createRefreshToken(member),
        )
    }
}

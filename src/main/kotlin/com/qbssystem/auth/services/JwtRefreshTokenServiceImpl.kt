package com.qbssystem.auth.services

import com.qbssystem.auth.repositories.JwtRefreshTokenRepository
import org.springframework.stereotype.Service

@Service
class JwtRefreshTokenServiceImpl(
    private val jwtRefreshTokenRepository: JwtRefreshTokenRepository
) : JwtRefreshTokenService {

    override fun revokeByAccountId(accountId: Long) {

        jwtRefreshTokenRepository.deleteByAccountId(accountId)
    }
}
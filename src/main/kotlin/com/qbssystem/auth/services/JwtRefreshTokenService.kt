package com.qbssystem.auth.services

interface JwtRefreshTokenService {

    fun revokeByAccountId(accountId: Long)
}
package com.qbssystem.auth.oauth2

import org.springframework.security.oauth2.common.OAuth2RefreshToken
import org.springframework.security.oauth2.provider.OAuth2Authentication

interface OAuth2JwtRefreshTokenStore {

    fun storeRefreshToken(refreshToken: OAuth2RefreshToken, authentication: OAuth2Authentication)

    fun readRefreshToken(tokenValue: String): OAuth2RefreshToken?

    fun removeRefreshToken(token: OAuth2RefreshToken)
}
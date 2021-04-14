package com.qbssystem.auth.oauth2

import org.springframework.security.oauth2.common.OAuth2RefreshToken
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

class OAuth2CombineTokenStore(
    jwtTokenEnhancer: JwtAccessTokenConverter,
    private val jwtRefreshTokenStore: OAuth2JwtRefreshTokenStore
) : JwtTokenStore(jwtTokenEnhancer) {

    override fun storeRefreshToken(refreshToken: OAuth2RefreshToken, authentication: OAuth2Authentication) {

        jwtRefreshTokenStore.storeRefreshToken(refreshToken, authentication)
    }

    @Throws(InvalidGrantException::class)
    override fun readRefreshToken(tokenValue: String): OAuth2RefreshToken {

        return jwtRefreshTokenStore
            .readRefreshToken(tokenValue)
            ?: throw InvalidGrantException("invalid_refresh_token")
    }

    override fun removeRefreshToken(token: OAuth2RefreshToken) {

        jwtRefreshTokenStore.removeRefreshToken(token)
    }
}
package com.qbssystem.auth.oauth2

import com.qbssystem.auth.entities.JwtRefreshToken
import com.qbssystem.auth.repositories.JwtRefreshTokenRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.oauth2.common.OAuth2RefreshToken
import org.springframework.security.oauth2.common.util.SerializationUtils
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.stereotype.Component
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@Component
class OAuth2JdbcJwtRefreshTokenStore(
    private val jwtRefreshTokenRepository: JwtRefreshTokenRepository
) : OAuth2JwtRefreshTokenStore {

    override fun storeRefreshToken(refreshToken: OAuth2RefreshToken, authentication: OAuth2Authentication) {

        jwtRefreshTokenRepository.save(
            JwtRefreshToken(
                id = hash(refreshToken.value),
                accountId = if (authentication.userAuthentication != null) {
                    (authentication.userAuthentication.principal as OAuth2User).id
                } else { -1 },
                token = SerializationUtils.serialize(refreshToken),
                authentication = SerializationUtils.serialize(authentication)
            )
        )
    }

    override fun readRefreshToken(tokenValue: String): OAuth2RefreshToken? {

        return jwtRefreshTokenRepository
            .findByIdOrNull(hash(tokenValue))
            ?.let { SerializationUtils.deserialize<OAuth2RefreshToken>(it.token) }
    }

    override fun removeRefreshToken(token: OAuth2RefreshToken) {

        jwtRefreshTokenRepository.deleteById(hash(token.value))
    }

    protected fun hash(value: String): String {

        val digest: MessageDigest = try {
            MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException("MD5 algorithm not available. Fatal (should be in the JDK).")
        }

        return try {
            val bytes = digest.digest(value.toByteArray(charset("UTF-8")))
            String.format("%032x", BigInteger(1, bytes))
        } catch (e: UnsupportedEncodingException) {
            throw IllegalStateException("UTF-8 encoding not available. Fatal (should be in the JDK).")
        }
    }
}
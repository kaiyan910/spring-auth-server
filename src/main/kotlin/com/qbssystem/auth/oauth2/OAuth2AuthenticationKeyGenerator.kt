package com.qbssystem.auth.oauth2

import org.springframework.security.oauth2.common.util.OAuth2Utils
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator
import java.security.MessageDigest

// 用嚟 CREATE 一個 UNIQUE 嘅 ID 比 oauth_access_token 嘅 authentication_id
class OAuth2AuthenticationKeyGenerator : AuthenticationKeyGenerator {

    override fun extractKey(authentication: OAuth2Authentication): String {

        val clientId = authentication.oAuth2Request.clientId
        val username = if (!authentication.isClientOnly) {
            authentication.name
        } else {
            "SYSTEM"
        }
        val scope = OAuth2Utils.formatParameterList(authentication.oAuth2Request.scope)

        return hash("$clientId+$username+$scope")
    }

    private fun hash(input: String): String {

        val HEX_CHARS = "0123456789ABCDEF"
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }

        return result.toString()
    }
}
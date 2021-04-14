package com.qbssystem.auth.oauth2.exception

import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException

// 如果 ACCOUNT 係 LOCKED / EXPIRED / DISABLED 就會 THROW 依個 EXCEPTION
class OAuthAccountNotAvailableException(
    message: String
) : ClientAuthenticationException(message) {

    override fun getOAuth2ErrorCode(): String = "account_not_available"
}
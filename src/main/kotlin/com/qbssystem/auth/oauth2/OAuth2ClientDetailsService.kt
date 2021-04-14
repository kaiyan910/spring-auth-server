package com.qbssystem.auth.oauth2

import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.ClientRegistrationException
import org.springframework.security.oauth2.provider.client.BaseClientDetails

class OAuth2ClientDetailsService : ClientDetailsService {

    private val inMemoryClientDetails = mapOf<String, ClientDetails>(
        "95b7a28d-7ebd-42b7-ba3a-2a6233d93a32" to BaseClientDetails().apply {
            clientId = "95b7a28d-7ebd-42b7-ba3a-2a6233d93a32"
            clientSecret = "{noop}secret"
            setScope(listOf("all"))
            setAuthorizedGrantTypes(listOf("password", "refresh_token", "client_credentials", "otp"))
            accessTokenValiditySeconds = 600
            refreshTokenValiditySeconds = 86400
        },
        "cc18c9d9-54f5-42b5-8c75-f023c1f16118" to BaseClientDetails().apply {
            clientId = "cc18c9d9-54f5-42b5-8c75-f023c1f16118"
            clientSecret = "{noop}secret"
            setScope(listOf("system"))
            setAuthorizedGrantTypes(listOf("refresh_token", "client_credentials"))
            accessTokenValiditySeconds = 604800
            refreshTokenValiditySeconds = 2592000
        }
    )

    @Throws(ClientRegistrationException::class)
    override fun loadClientByClientId(clientId: String): ClientDetails {
        return inMemoryClientDetails[clientId] ?: throw ClientRegistrationException("clientId $clientId not found")
    }
}
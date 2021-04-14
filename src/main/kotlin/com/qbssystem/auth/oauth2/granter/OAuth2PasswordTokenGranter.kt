package com.qbssystem.auth.oauth2.granter

import com.qbssystem.auth.oauth2.exception.OAuthAccountNotAvailableException
import org.springframework.security.authentication.*
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException
import org.springframework.security.oauth2.provider.*
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices

class OAuth2PasswordTokenGranter(
    private val authenticationManager: AuthenticationManager,
    tokenServices: AuthorizationServerTokenServices,
    clientDetailsService: ClientDetailsService,
    requestFactory: OAuth2RequestFactory
) : ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory) {

    @Throws(ClientAuthenticationException::class)
    override fun getOAuth2Authentication(client: ClientDetails, tokenRequest: TokenRequest): OAuth2Authentication {

        val parameters: MutableMap<String, String> = LinkedHashMap(tokenRequest.requestParameters)
        val username = parameters["username"]
        val password = parameters["password"]

        // Protect from downstream leaks of password
        parameters.remove("password")

        var userAuth: Authentication = UsernamePasswordAuthenticationToken(username, password)
        (userAuth as AbstractAuthenticationToken).details = parameters

        userAuth = try {
            authenticationManager.authenticate(userAuth)
        } catch (ase: AccountStatusException) {
            // covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw OAuthAccountNotAvailableException(ase.message ?: "account is disabled")
        } catch (e: BadCredentialsException) {
            // If the username/password are wrong the spec says we should send 400/invalid grant
            throw InvalidGrantException(e.message)
        }

        if (userAuth == null || !userAuth.isAuthenticated) {
            throw InvalidGrantException("Could not authenticate user: $username")
        }

        val storedOAuth2Request = requestFactory.createOAuth2Request(client, tokenRequest)
        return OAuth2Authentication(storedOAuth2Request, userAuth)
    }
}
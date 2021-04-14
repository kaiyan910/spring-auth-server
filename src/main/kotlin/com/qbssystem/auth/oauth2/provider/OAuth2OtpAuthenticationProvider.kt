package com.qbssystem.auth.oauth2.provider

import com.qbssystem.auth.oauth2.authentication.OtpAuthenticationToken
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService

class OAuth2OtpAuthenticationProvider(
    private val userDetailsService: UserDetailsService
) : AuthenticationProvider {

    @Throws(BadCredentialsException::class)
    override fun authenticate(authentication: Authentication): Authentication {

        val otpAuthenticationToken = authentication as OtpAuthenticationToken
        val username = otpAuthenticationToken.principal as String
        val otp = otpAuthenticationToken.credentials as String

        //TODO: check username not blank
        //TODO: check otp not blank

        //TODO: replace by database
        if (otp != "999999") {
            throw BadCredentialsException("invalid otp provided")
        }

        val account = userDetailsService.loadUserByUsername(username) as User
        account.eraseCredentials()

        return OtpAuthenticationToken(account, authentication.credentials, account.authorities)
            .apply { details = authentication.details }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication.isAssignableFrom(OtpAuthenticationToken::class.java)
    }
}
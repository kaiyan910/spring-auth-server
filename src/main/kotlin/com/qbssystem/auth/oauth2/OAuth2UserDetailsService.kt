package com.qbssystem.auth.oauth2

import com.qbssystem.auth.repositories.AccountRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class OAuth2UserDetailsService(
    private val accountRepository: AccountRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        val account = accountRepository
            .findByUsername(username)
            ?: throw UsernameNotFoundException("$username not found")

        return OAuth2User(
            account.id!!,
            account.username,
            account.password,
            // Disabled indicates an account has been administratively or automatically disabled for some reason.
            // Usually some action is required to release it.
            account.enabled,
            // Indicates whether the user's account has expired.
            !account.expired,
            // Indicates whether the user's credentials (password) has expired.
            true,
            // Locked indicates an account has been automatically suspended due to invalid login attempts.
            // Usually the passage of time or (less often) requesting manual unlocking is required to release it.
            !account.locked,
            listOf(SimpleGrantedAuthority("ROLE_${account.role}"))
        )
    }
}
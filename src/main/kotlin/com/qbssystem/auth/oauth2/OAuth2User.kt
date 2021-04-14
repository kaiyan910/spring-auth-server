package com.qbssystem.auth.oauth2

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class OAuth2User(
    val id: Long,
    username: String,
    password: String,
    enabled: Boolean,
    accountNonExpired: Boolean,
    credentialsNonExpired: Boolean,
    accountNonLocked: Boolean,
    authorities: List<GrantedAuthority>
) : User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities)
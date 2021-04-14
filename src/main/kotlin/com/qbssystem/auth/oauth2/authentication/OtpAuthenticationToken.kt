package com.qbssystem.auth.oauth2.authentication

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class OtpAuthenticationToken : AbstractAuthenticationToken {

    private var principal: Any? = null
    private var credentials: Any? = null

    constructor(principal: Any, credentials: Any?): super(null) {
        this.principal = principal
        this.credentials = credentials
    }

    constructor(principal: Any, credentials: Any?, collection: Collection<GrantedAuthority>): super(collection) {

        this.principal = principal
        this.credentials = credentials
        this.isAuthenticated = true
    }

    override fun getCredentials(): Any? {
        return this.credentials
    }

    override fun getPrincipal(): Any? {
        return this.principal
    }
}
package com.qbssystem.auth.entities

import com.qbssystem.auth.domain.AccountRole
import javax.persistence.*

@Entity
@Table(name = "account")
class Account(
    @Column(nullable = false, unique = true, name = "username")
    val username: String,
    @Column(nullable = false, name = "password")
    val password: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    val role: AccountRole,
    @Column(name = "name")
    var name: String?,
    @Column(name = "phone")
    var phone: String?,
    @Column(name = "email")
    var email: String?,
    @Column(nullable = false, name = "enabled")
    var enabled: Boolean = true,
    @Column(nullable = false, name = "locked")
    var locked: Boolean = false,
    @Column(nullable = false, name = "expired")
    var expired: Boolean = false
) : BaseEntity()
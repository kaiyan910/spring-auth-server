package com.qbssystem.auth.entities

import javax.persistence.*

@Entity
@Table(name = "jwt_refresh_token")
class JwtRefreshToken(
    @Id
    @Column(nullable = false, unique = true, name = "id")
    var id: String,
    @Column(nullable = false,name = "account_id")
    val accountId: Long,
    @Lob
    @Column(nullable = false, name = "token")
    val token: ByteArray,
    @Lob
    @Column(nullable = false, name = "authentication")
    val authentication: ByteArray
)
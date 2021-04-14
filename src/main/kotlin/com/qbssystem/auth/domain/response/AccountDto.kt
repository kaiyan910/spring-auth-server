package com.qbssystem.auth.domain.response

import com.qbssystem.auth.domain.AccountRole
import com.qbssystem.auth.entities.Account

class AccountDto(
    val username: String,
    val role: AccountRole,
    val name: String?,
    val phone: String?,
    val email: String?,
    val enabled: Boolean,
    val locked: Boolean,
    val expired: Boolean
) {

    companion object {

        fun from(entity: Account): AccountDto = AccountDto(
            username = entity.username,
            role = entity.role,
            name = entity.name,
            phone = entity.phone,
            email = entity.email,
            enabled = entity.enabled,
            locked = entity.locked,
            expired = entity.expired
        )
    }
}
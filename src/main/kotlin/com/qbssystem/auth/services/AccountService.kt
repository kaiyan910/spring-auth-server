package com.qbssystem.auth.services

import com.qbssystem.auth.domain.request.CreateAccountDto
import com.qbssystem.auth.entities.Account

interface AccountService {

    fun create(dto: CreateAccountDto): Account
    fun isPhoneBelongToAccount(phone: String): Boolean
    fun disable(accountId: Long)
}
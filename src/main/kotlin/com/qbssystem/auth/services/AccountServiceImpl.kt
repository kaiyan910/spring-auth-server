package com.qbssystem.auth.services

import com.qbssystem.auth.domain.AccountRole
import com.qbssystem.auth.domain.request.CreateAccountDto
import com.qbssystem.auth.entities.Account
import com.qbssystem.auth.repositories.AccountRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder
) : AccountService {

    override fun create(dto: CreateAccountDto): Account {

        //TODO: validate the parameters

        return accountRepository.save(
            Account(
                dto.username,
                passwordEncoder.encode(dto.password),
                role = AccountRole.ADMIN,
                name = dto.name,
                phone = dto.phone,
                email = dto.email,
                enabled = true,
                locked = false,
                expired = false
            )
        )
    }

    override fun isPhoneBelongToAccount(phone: String): Boolean {

        return accountRepository.existsByPhone(phone)
    }

    override fun disable(accountId: Long) {

        accountRepository.disable(accountId)
    }
}
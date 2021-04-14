package com.qbssystem.auth.controller

import com.qbssystem.auth.domain.request.CreateAccountDto
import com.qbssystem.auth.domain.response.AccountDto
import com.qbssystem.auth.services.AccountService
import com.qbssystem.auth.services.JwtRefreshTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/api/v1/account")
@Validated
class AccountController {

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var jwtRefreshTokenService: JwtRefreshTokenService

    @PostMapping
    fun create(@Valid @RequestBody dto: CreateAccountDto): AccountDto {

        return accountService
            .create(dto)
            .let { AccountDto.from(it) }
    }

    @GetMapping("/otp/{phone}")
    fun requestOtp(
        @PathVariable("phone") @NotBlank phone: String
    ) {

        if (!accountService.isPhoneBelongToAccount(phone)) {
            //TODO: throw error
        }

        //TODO: send OTP
    }

    @PutMapping("/disable/{account-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun disable(@PathVariable("account-id") @Positive accountId: Long) {

        accountService.disable(accountId)
    }

    @PutMapping("/revoke/{account-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun revoke(@PathVariable("account-id") @Positive accountId: Long) {

        jwtRefreshTokenService.revokeByAccountId(accountId)
    }
}
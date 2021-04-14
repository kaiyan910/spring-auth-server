package com.qbssystem.auth.domain.request

import javax.validation.constraints.NotBlank

data class CreateAccountDto(
    @field:NotBlank val username: String,
    @field:NotBlank val password: String,
    val name: String?,
    val phone: String?,
    val email: String?
)
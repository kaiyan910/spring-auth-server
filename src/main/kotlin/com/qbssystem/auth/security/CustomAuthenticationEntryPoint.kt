package com.qbssystem.auth.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {

    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {

        // 當系統發現個 REQUEST 係一個未做認證 (AUTHORIZATION) 就會 RESPONSE 比個 USER：
        // STATELESS 嘅 APPLICATION 會番一個 401 RESPONSE
        // STATEFUL 嘅就可能 REDIRECT 301 個 USER 去 LOGIN PAGE

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        ObjectMapper()
            .writeValue(
                response.outputStream,
                mapOf(
                    "code" to "401",
                    "message" to "unauthorized_request"
                )
            )
    }
}
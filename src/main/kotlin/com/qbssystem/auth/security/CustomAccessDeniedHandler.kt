package com.qbssystem.auth.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAccessDeniedHandler : AccessDeniedHandler {

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, accessDeniedException: AccessDeniedException) {

        // 當系統發現依個 REQUEST 係唔夠權限做就會 RESPONSE 比 USER：
        // STATELESS 嘅 APPLICATION 會番一個 403 RESPONSE
        // STATEFUL 嘅就可能 REDIRECT 301 個 USER 去 FORBIDDEN PAGE

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_FORBIDDEN

        ObjectMapper()
            .writeValue(
                response.outputStream,
                mapOf(
                    "code" to "403",
                    "message" to "forbidden"
                )
            )
    }
}
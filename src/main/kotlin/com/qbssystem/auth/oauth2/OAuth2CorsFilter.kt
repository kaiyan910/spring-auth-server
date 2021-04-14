package com.qbssystem.auth.oauth2

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse

/**
 * Auth Server 要用依個 FILTER CLASS 去做 CORS，
 * 唔知做咩喺 RESOURCE SERVER CONFIG 唔 WORK
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class OAuth2CorsFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        val httpResponse = response as HttpServletResponse

        httpResponse.setHeader("Access-Control-Allow-Origin", "*")
        httpResponse.setHeader("Access-Control-Allow-Methods", "PATCH,POST,GET,OPTIONS,DELETE")
        httpResponse.setHeader("Access-Control-Max-Age", "3600")
        httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN")

        chain.doFilter(request, response)
    }
}
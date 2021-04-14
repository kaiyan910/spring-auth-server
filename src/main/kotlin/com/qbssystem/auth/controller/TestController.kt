package com.qbssystem.auth.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/test")
class TestController {

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('system')")
    fun me(oAuth2Authentication: OAuth2Authentication): String {
        return oAuth2Authentication.name
    }
}
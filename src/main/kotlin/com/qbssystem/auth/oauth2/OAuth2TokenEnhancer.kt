package com.qbssystem.auth.oauth2

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer

class OAuth2TokenEnhancer : TokenEnhancer {

    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {

        //TODO: 如果要喺 TOKEN RESPONSE 到加額外嘅 FIELD 可以咁樣加
        val additionalInfo: MutableMap<String, Any?> = HashMap()
        additionalInfo["extra"] = "Hello World"
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo

        return accessToken
    }
}
package com.qbssystem.auth.oauth2

import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.DefaultTokenServices

open class OAuth2TokenServices : DefaultTokenServices() {

    override fun createAccessToken(authentication: OAuth2Authentication): OAuth2AccessToken {

        // 確定一個 ACCOUNT 喺一個 CLIENT_ID 只有一個 TOKEN 係 ACTIVE 緊
        // 如果想做到 MULTIPLE DEVICE LOGIN 就 COMMENT 咗佢

        /*if (!authentication.isClientOnly) {

            // CHECK 下 UNDER 依個 authentication 底下有無 TOKEN GENERATE 過
            // 有就 REVOKE 咗佢等佢再用唔到依條 TOKEN
            val token = getAccessToken(authentication)

            if (token != null) {
                revokeToken(token.value)
            }
        }
        */

        //TODO: 如果係 JWT 又想一個 USER 得一條 TOKEN 可以喺到 CALL JwtRefreshTokenService.revokeByAccountId

        return super.createAccessToken(authentication)
    }
}
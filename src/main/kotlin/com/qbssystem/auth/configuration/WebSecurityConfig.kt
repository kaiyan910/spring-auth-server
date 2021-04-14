package com.qbssystem.auth.configuration

import com.qbssystem.auth.oauth2.OAuth2UserDetailsService
import com.qbssystem.auth.oauth2.provider.OAuth2OtpAuthenticationProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val userDetailsService: OAuth2UserDetailsService
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {

        // 一定要 COMMENT 下面嗰行，唔係佢就會行 DEFAULT 嘅 BUILDER
        //super.configure(auth)

        auth
            // 一定要 SET 依個因為無 SET DaoAuthenticationProvider 就 initialize 唔到
            // PASSWORD 個 GRANT-TYPE 就唔會 WORK
            .userDetailsService(userDetailsService)
            .and()
            .authenticationProvider(
                OAuth2OtpAuthenticationProvider(userDetailsService)
            )
            // 用嚟比 REFRESH TOKEN 時 AuthenticationManager 重新認證個用戶用
            // 如果個用戶喺 REFRESH TOKEN 時已經比人 DISABLED 就唔可以拎新 TOKEN
            .authenticationProvider(

                PreAuthenticatedAuthenticationProvider()
                    .apply { setPreAuthenticatedUserDetailsService(UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>(userDetailsService)) }
            )
    }

    @Bean
    // 用嚟 ENCRYPT 碼密用嘅 ENCODER
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    // 一定要 EXPOSE 做 BEAN 先可以 APPLY 番 configure(...) 入面嘅設定去 AuthenticationManager
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}
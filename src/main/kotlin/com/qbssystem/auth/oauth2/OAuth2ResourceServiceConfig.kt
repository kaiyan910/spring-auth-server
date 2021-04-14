package com.qbssystem.auth.oauth2

import com.qbssystem.auth.security.CustomAccessDeniedHandler
import com.qbssystem.auth.security.CustomAuthenticationEntryPoint
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer

@Configuration
@EnableResourceServer
class OAuth2ResourceServiceConfig : ResourceServerConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http.cors()
            .and()
            // CSRF is not needed for stateless application
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/api/v1/account").permitAll()
            .anyRequest().authenticated()
    }

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.authenticationEntryPoint(CustomAuthenticationEntryPoint())
        resources.accessDeniedHandler(CustomAccessDeniedHandler())
    }
}
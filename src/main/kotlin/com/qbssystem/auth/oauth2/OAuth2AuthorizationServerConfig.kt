package com.qbssystem.auth.oauth2

import com.qbssystem.auth.oauth2.granter.OAuth2OtpTokenGranter
import com.qbssystem.auth.oauth2.granter.OAuth2PasswordTokenGranter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.CompositeTokenGranter
import org.springframework.security.oauth2.provider.TokenGranter
import org.springframework.security.oauth2.provider.approval.ApprovalStore
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.*
import javax.sql.DataSource

@Configuration
@EnableAuthorizationServer
class OAuth2AuthorizationServerConfig(
    private val authenticationManager: AuthenticationManager,
    private val dataSource: DataSource,
    private val jwtRefreshTokenStore: OAuth2JwtRefreshTokenStore
) : AuthorizationServerConfigurerAdapter() {

    @Value("\${security.oauth2.resource.jwt.key-alias}")
    private lateinit var keyAlias: String

    @Value("\${security.oauth2.resource.jwt.key-store-password}")
    private lateinit var keyStorePassword: String

    override fun configure(security: AuthorizationServerSecurityConfigurer) {

        security
            // Exposes public key for token verification if using JWT tokens
            .tokenKeyAccess("permitAll()")
            // ??? RESOURCE SERVER ?????? CHECK ??? TOKEN ?????? VALID
            .checkTokenAccess("isAuthenticated()")
            .allowFormAuthenticationForClients()
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {

        clients
            .withClientDetails(OAuth2ClientDetailsService())
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {

        endpoints
            .authenticationManager(authenticationManager)
            .tokenServices(tokenServices())
            .tokenGranter(tokenGranter(endpoints))
    }

    @Bean
    fun tokenServices(): OAuth2TokenServices = OAuth2TokenServices()
        .apply {

            // ????????? REFRESH TOKEN ??????????????? USER ???????????? ENABLED
            setAuthenticationManager(authenticationManager)

            setTokenStore(tokenStore())
            setReuseRefreshToken(false)
            setSupportRefreshToken(true)

            // ????????? JWT ??? Enhancer ?????????????????? JWT format ?????????????????? EXTRA CLAIMS ????????? TokenEnhancerChain
            // setTokenEnhancer(OAuth2TokenEnhancer())

            setTokenEnhancer(
                TokenEnhancerChain()
                    .apply { setTokenEnhancers(listOf(OAuth2TokenEnhancer(), jwtTokenEnhancer())) }
            )
        }

    @Bean
    fun tokenStore(): TokenStore {

        return OAuth2CombineTokenStore(jwtTokenEnhancer(), jwtRefreshTokenStore)
    }

    /*@Bean
    fun tokenStore(): TokenStore {

        return JdbcTokenStore(dataSource)
            .apply { setAuthenticationKeyGenerator(OAuth2AuthenticationKeyGenerator()) }
    }*/

    @Bean
    fun jwtTokenEnhancer(): JwtAccessTokenConverter {

        return JwtAccessTokenConverter()
            .apply {
                setKeyPair(
                    KeyStoreKeyFactory(
                        ClassPathResource("keystore.jks"),
                        keyStorePassword.toCharArray()
                    ).getKeyPair(keyAlias)
                )
            }
    }

    private fun tokenGranter(endpoints: AuthorizationServerEndpointsConfigurer): TokenGranter {

        return mutableListOf<TokenGranter>()
            .apply {
                add(OAuth2PasswordTokenGranter(authenticationManager, endpoints.tokenServices, endpoints.clientDetailsService, endpoints.oAuth2RequestFactory))
                add(OAuth2OtpTokenGranter(authenticationManager, endpoints.tokenServices, endpoints.clientDetailsService, endpoints.oAuth2RequestFactory))
                add(RefreshTokenGranter(endpoints.tokenServices, endpoints.clientDetailsService, endpoints.oAuth2RequestFactory))
                add(ClientCredentialsTokenGranter(endpoints.tokenServices, endpoints.clientDetailsService, endpoints.oAuth2RequestFactory))
            }
            .let { CompositeTokenGranter(it) }
    }
}
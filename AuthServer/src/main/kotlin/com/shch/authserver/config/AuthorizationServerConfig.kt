package com.shch.authserver.config

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import com.shch.authserver.extmod.pwd.PasswordAuthenticationConverter
import com.shch.authserver.extmod.pwd.PasswordAuthenticationProvider
import com.shch.authserver.handler.RestAuthenticationFailureHandler
import com.shch.authserver.handler.RestAuthenticationSuccessHandler
import com.shch.authserver.model.po.UserDTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.OAuth2Token
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.authorization.*
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.security.oauth2.server.authorization.token.*
import org.springframework.security.web.SecurityFilterChain
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.time.Duration
import java.util.*


@Configuration
class AuthorizationServerConfig {

    private var jwtCustomizer = OAuth2TokenCustomizer<JwtEncodingContext> { context ->
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.tokenType)
            && context.getPrincipal<AbstractAuthenticationToken>() is UsernamePasswordAuthenticationToken
        ) {
            val userpwdToken = context.getPrincipal<UsernamePasswordAuthenticationToken>()
            // Customize headers/claims for access_token
            Optional.ofNullable(userpwdToken.principal).ifPresent { principal ->
                val claims = context.claims;
                if (principal is UserDTO) {
                    val userDetails = principal
                    // 系统用户添加自定义字段
                    val userId = userDetails.uid;
                    claims.claim("uid", userId);  // 添加系统用户ID

                    // 角色集合存JWT
                    val authorities: MutableSet<String> = mutableSetOf()
                    for (authority in userpwdToken.authorities) {
                        authorities.add(authority.authority.split("_")[1])
                    }

//                    var authorities: Set<String> = AuthorityUtils.authorityListToSet(userpwdToken.authorities)


                    claims.claim("role", authorities)

                    // 权限集合存Redis(数据多)
//                    Set<String> perms = userDetails.getPerms();
//                    redisTemplate.opsForValue().set(SecurityConstants.USER_PERMS_CACHE_PREFIX + userId, perms);

                }

            }
        }
    }


    /**
     * 授权服务器端点配置
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun authorizationServerSecurityFilterChain(
        http: HttpSecurity,
        authenticationManager: AuthenticationManager,
        authorizationService: OAuth2AuthorizationService,
        tokenGenerator: OAuth2TokenGenerator<OAuth2Token>,

        ): SecurityFilterChain {

        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http)

        http.getConfigurer(OAuth2AuthorizationServerConfigurer::class.java)
            .tokenEndpoint { tokenEndpoint ->
                tokenEndpoint
                    .accessTokenRequestConverter(PasswordAuthenticationConverter())
                    .authenticationProvider(
                        PasswordAuthenticationProvider(
                            authenticationManager,
                            authorizationService,
                            tokenGenerator
                        )
                    )
                    .accessTokenResponseHandler(RestAuthenticationSuccessHandler()) // 自定义成功响应
                    .errorResponseHandler(RestAuthenticationFailureHandler()) // 自定义失败响应
            }


        return http.build();
    }

    @Bean // <5>
    fun jwkSource(): JWKSource<SecurityContext> {
        val keyPair: KeyPair = generateRsaKey()
        val publicKey = keyPair.public as RSAPublicKey
        val privateKey = keyPair.private as RSAPrivateKey
        // @formatter:off
        val rsaKey: RSAKey = RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build()
        // @formatter:on
        val jwkSet: JWKSet = JWKSet(rsaKey)
        return ImmutableJWKSet<SecurityContext>(jwkSet)
    }

    private fun generateRsaKey(): KeyPair { // <6>
        val keyPair: KeyPair
        try {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(2048)
            keyPair = keyPairGenerator.generateKeyPair()
        } catch (ex: Exception) {
            throw IllegalStateException(ex)
        }
        return keyPair
    }

    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext?>?): JwtDecoder {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)
    }

    @Bean
    fun authorizationServerSettings(): AuthorizationServerSettings {
        return AuthorizationServerSettings.builder().build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }


    @Bean
    fun registeredClientRepository(jdbcTemplate: JdbcTemplate): RegisteredClientRepository {
        val registeredClientRepository = JdbcRegisteredClientRepository(jdbcTemplate)

        // 初始化 OAuth2 客户端
        initAppClient(registeredClientRepository)
//        initMallAdminClient(registeredClientRepository)

        return registeredClientRepository
    }

    /**
     * 初始化创建商城管理客户端
     */
    private fun initAppClient(registeredClientRepository: JdbcRegisteredClientRepository) {
        val clientId = "mall-admin"
        val clientSecret = "123456"
        val clientName = "商城管理客户端"

        /*
          如果使用明文，客户端认证时会自动升级加密方式，换句话说直接修改客户端密码，所以直接使用 bcrypt 加密避免不必要的麻烦
          官方ISSUE： https://github.com/spring-projects/spring-authorization-server/issues/1099
         */
        val encodeSecret = passwordEncoder().encode(clientSecret)

        val registeredMallAdminClient = registeredClientRepository.findByClientId(clientId)
        val id = if (registeredMallAdminClient != null) registeredMallAdminClient.id else UUID.randomUUID().toString()

        val mallAppClient = RegisteredClient.withId(id)
            .clientId(clientId)
            .clientSecret(encodeSecret)
            .clientName(clientName)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .authorizationGrantType(AuthorizationGrantType.PASSWORD) // 密码模式
            .redirectUri("http://127.0.0.1:8080/authorized")
            .postLogoutRedirectUri("http://127.0.0.1:8080/logged-out")
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build())
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build()
        registeredClientRepository.save(mallAppClient)
    }


    @Bean
    fun authorizationService(
        jdbcTemplate: JdbcTemplate,
        registeredClientRepository: RegisteredClientRepository
    ): OAuth2AuthorizationService {
        val service = JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository)
//        val rowMapper = JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository)
//        rowMapper.setLobHandler(DefaultLobHandler())
//        val objectMapper = ObjectMapper()
//        val classLoader = JdbcOAuth2AuthorizationService::class.java.classLoader
//        val securityModules = SecurityJackson2Modules.getModules(classLoader)
//        objectMapper.registerModules(securityModules)
//        objectMapper.registerModule(OAuth2AuthorizationServerJackson2Module())
//        // 使用刷新模式，需要从 oauth2_authorization 表反序列化attributes字段得到用户信息(SysUserDetails)
//        objectMapper.addMixIn(SysUserDetails::class.java, SysUserMixin::class.java)
//        objectMapper.addMixIn(Long::class.java, Any::class.java)
//
//        rowMapper.setObjectMapper(objectMapper)
//        service.setAuthorizationRowMapper(rowMapper)
        return service
    }

    @Bean
    fun authorizationConsentService(
        jdbcTemplate: JdbcTemplate?,
        registeredClientRepository: RegisteredClientRepository?
    ): OAuth2AuthorizationConsentService {
        // Will be used by the ConsentController
        return JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository)
    }

//    @Bean
//    fun idTokenCustomizer():  OAuth2TokenCustomizer<JwtEncodingContext> {
//        return Federa
//    }

    @Bean
    fun tokenGenerator(jwkSource: JWKSource<SecurityContext>): OAuth2TokenGenerator<OAuth2Token> {
        val jwtGenerator = JwtGenerator(NimbusJwtEncoder(jwkSource))
        jwtGenerator.setJwtCustomizer(jwtCustomizer)

        val accessTokenGenerator = OAuth2AccessTokenGenerator()
        val refreshTokenGenerator = OAuth2RefreshTokenGenerator()
        return DelegatingOAuth2TokenGenerator(
            jwtGenerator, accessTokenGenerator, refreshTokenGenerator
        )
    }


    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        val a = authenticationConfiguration.authenticationManager
        return a
    }

}
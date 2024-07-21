package com.shch.resdemo.config

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.JWSKeySelector
import com.nimbusds.jose.proc.JWSVerificationKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import com.nimbusds.jwt.proc.JWTClaimsSetVerifier
import com.shch.resdemo.handler.CusJwtGrantedAuthoritiesConverter
import com.shch.resdemo.handler.LocalAccessDeniedHandler
import com.shch.resdemo.handler.LocalAuthenticationEntryPoint
import com.shch.starterwebext.model.MessageSourceConfig
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*
import kotlin.collections.HashSet

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@Import(OAuth2ResourceServerAutoConfiguration::class)
class ResourceServerSecurityConfig {

    /**
     * Spring Security 安全过滤器链配置
     */
    @Bean
    @Order(0)
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .authorizeHttpRequests { requestMatcherRegistry ->
                requestMatcherRegistry.requestMatchers("/hello").permitAll()
                requestMatcherRegistry.anyRequest().authenticated()
            }
            .csrf { it.disable() }
            .formLogin {
                it.disable()
//                Customizer.withDefaults()
            }
//            .formLogin(Customizer.withDefaults())
        http
            .exceptionHandling { exceptions ->
                exceptions
                    // 用来解决匿名用户访问无权限资源时的异常
                    .authenticationEntryPoint(LocalAuthenticationEntryPoint())
                    // 用来解决认证过的用户访问无权限资源时的异常, 跳转的页面, 如果自定义返回内容, 请使用accessDeniedHandler方法
                    // 用来解决认证过的用户访问无权限资源时的异常
                    .accessDeniedHandler(LocalAccessDeniedHandler())
                    .accessDeniedPage("/error")
            }
            .oauth2ResourceServer {

                it.jwt(Customizer.withDefaults()).jwt { jwt ->
                    val jwtAuthenticationConverter = JwtAuthenticationConverter()
                    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(CusJwtGrantedAuthoritiesConverter())
                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)
                }.accessDeniedHandler(LocalAccessDeniedHandler())
                    .authenticationEntryPoint(LocalAuthenticationEntryPoint())

            }


        return http.build()
    }

    /**
     * Spring Security 自定义安全配置
     */
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->  // 不走过滤器链(场景：静态资源js、css、html)
            web.ignoring().requestMatchers(
                "/webjars/**",
                "/doc.html",
                "/swagger-resources/**",
                "/v3/api-docs/**",
                "/swagger-ui/**"
            )
        }
    }

//    @Bean
//    // @formatter:on
//    fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder {
//        val jwsAlgs: MutableSet<JWSAlgorithm> = HashSet()
//        jwsAlgs.addAll(JWSAlgorithm.Family.RSA)
//        jwsAlgs.addAll(JWSAlgorithm.Family.EC)
//        jwsAlgs.addAll(JWSAlgorithm.Family.HMAC_SHA)
//        val jwtProcessor: ConfigurableJWTProcessor<SecurityContext> = DefaultJWTProcessor()
//        val jwsKeySelector: JWSKeySelector<SecurityContext> = JWSVerificationKeySelector(jwsAlgs, jwkSource)
//        jwtProcessor.jwsKeySelector = jwsKeySelector
//        // Override the default Nimbus claims set verifier as NimbusJwtDecoder handles it
//        // instead
//        jwtProcessor.jwtClaimsSetVerifier =
//            JWTClaimsSetVerifier { claims: JWTClaimsSet?, context: SecurityContext? -> }
//        return NimbusJwtDecoder(jwtProcessor)
//    }

//    private fun generateRsaKey(): KeyPair { // <6>
//        val keyPair: KeyPair
//        try {
//            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
//            keyPairGenerator.initialize(2048)
//            keyPair = keyPairGenerator.generateKeyPair()
//        } catch (ex: Exception) {
//            throw IllegalStateException(ex)
//        }
//        return keyPair
//    }
//
//
//    @Bean // <5>
//    fun jwkSource(): JWKSource<SecurityContext> {
//        val keyPair: KeyPair = generateRsaKey()
//        val publicKey = keyPair.public as RSAPublicKey
//        val privateKey = keyPair.private as RSAPrivateKey
//        // @formatter:off
//        val rsaKey: RSAKey = RSAKey.Builder(publicKey)
//            .privateKey(privateKey)
//            .keyID(UUID.randomUUID().toString())
//            .build()
//        // @formatter:on
//        val jwkSet: JWKSet = JWKSet(rsaKey)
//        return ImmutableJWKSet<SecurityContext>(jwkSet)
//    }

    @Bean
    fun getMessageSourceConfig(): MessageSourceConfig {
        return MessageSourceConfig("i18n/messages")
    }
}
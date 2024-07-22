package com.shch.starterwebext.config

import com.shch.starterwebext.handler.CusJwtGrantedAuthoritiesConverter
import com.shch.starterwebext.handler.LocalAccessDeniedHandler
import com.shch.starterwebext.handler.LocalAuthenticationEntryPoint
import com.shch.starterwebext.model.MessageSourceConfig
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Import(OAuth2ResourceServerAutoConfiguration::class)
abstract class ResourceServerSecurityConfigAdapter {

    abstract fun securityFilterChain(http: HttpSecurity)

    /**
     * Spring Security 安全过滤器链配置
     */
    @Bean
    @Order(0)
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf { it.disable() }
            .formLogin {
                it.disable()
            }
            .exceptionHandling { exceptions ->
                exceptions
                    // 用来解决匿名用户访问无权限资源时的异常
                    .authenticationEntryPoint(LocalAuthenticationEntryPoint())
                    // 用来解决认证过的用户访问无权限资源时的异常, 跳转的页面, 如果自定义返回内容, 请使用accessDeniedHandler方法
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

        securityFilterChain(http)

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

    @Bean
    fun getMessageSourceConfig(): MessageSourceConfig {
        return MessageSourceConfig("i18n/messages")
    }

}
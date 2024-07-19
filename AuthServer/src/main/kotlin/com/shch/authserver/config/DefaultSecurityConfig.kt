package com.shch.authserver.config

import com.shch.authserver.handler.LocalAccessDeniedHandler
import com.shch.authserver.handler.LocalAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.oauth2.server.resource.authentication.JwtBearerTokenAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
class DefaultSecurityConfig {
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
            .csrf {it.disable() }
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

                it.jwt(Customizer.withDefaults())

                it.accessDeniedHandler(LocalAccessDeniedHandler())
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
}
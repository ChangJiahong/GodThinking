package com.shch.resdemo.config

import com.shch.starterwebext.config.ResourceServerSecurityConfigAdapter
import com.shch.starterwebext.handler.CusJwtGrantedAuthoritiesConverter
import com.shch.starterwebext.handler.LocalAccessDeniedHandler
import com.shch.starterwebext.handler.LocalAuthenticationEntryPoint
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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import java.util.*

@Configuration(proxyBeanMethods = false)
class ResourceServerSecurityConfig : ResourceServerSecurityConfigAdapter() {
    override fun securityFilterChain(http: HttpSecurity) {
        http
            .authorizeHttpRequests { requestMatcherRegistry ->
                requestMatcherRegistry.requestMatchers("/hello").permitAll()
                requestMatcherRegistry.anyRequest().authenticated()
            }
    }
}
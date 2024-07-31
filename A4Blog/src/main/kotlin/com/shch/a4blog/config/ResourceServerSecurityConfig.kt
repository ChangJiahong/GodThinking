package com.shch.a4blog.config

import com.shch.a4blog.handler.CookieBearerTokenResolver
import com.shch.a4blog.handler.MyAuthenticationEntryPoint
import com.shch.starterwebext.config.ResourceServerSecurityConfigAdapter
import com.shch.starterwebext.handler.LocalAccessDeniedHandler
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver
import org.springframework.security.oauth2.server.resource.web.HeaderBearerTokenResolver
import java.util.*

@Configuration(proxyBeanMethods = false)
class ResourceServerSecurityConfig : ResourceServerSecurityConfigAdapter() {
    override fun securityFilterChain(http: HttpSecurity) {
        http
            .authorizeHttpRequests { requestMatcherRegistry ->
                requestMatcherRegistry.requestMatchers("/admin/login").permitAll()
                requestMatcherRegistry.requestMatchers("/admin/te").permitAll()
                requestMatcherRegistry.requestMatchers("/admin/**").authenticated()
//                requestMatcherRegistry.requestMatchers("/**").permitAll()
                requestMatcherRegistry.anyRequest().permitAll()
            }
            .exceptionHandling { exceptions ->
                exceptions
                    // 用来解决匿名用户访问无权限资源时的异常
                    .authenticationEntryPoint(MyAuthenticationEntryPoint())
                    // 用来解决认证过的用户访问无权限资源时的异常, 跳转的页面, 如果自定义返回内容, 请使用accessDeniedHandler方法
                    .accessDeniedHandler(LocalAccessDeniedHandler())
                    .accessDeniedPage("/error")
            }
            .oauth2ResourceServer {
                it.bearerTokenResolver(CookieBearerTokenResolver())
                it.authenticationEntryPoint(MyAuthenticationEntryPoint())
            }
    }
}
package com.shch.authserver.extmod.oidc

import com.shch.authserver.service.impl.CustomOidcUserInfoService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken
import org.springframework.security.web.authentication.AuthenticationConverter

/**
 * 自定义 OIDC 认证转换器
 *
 * @author Ray Hao
 * @since 3.1.0
 */
class CustomOidcAuthenticationConverter(
    private val customOidcUserInfoService: CustomOidcUserInfoService
) :
    AuthenticationConverter {
    override fun convert(request: HttpServletRequest): Authentication {
        val authentication = SecurityContextHolder.getContext().authentication
        val customOidcUserInfo = customOidcUserInfoService.loadUserByUsername(authentication.name)
        return OidcUserInfoAuthenticationToken(authentication, customOidcUserInfo)
    }
}

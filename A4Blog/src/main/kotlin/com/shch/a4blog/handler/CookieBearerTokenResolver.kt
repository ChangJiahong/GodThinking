package com.shch.a4blog.handler

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.server.resource.BearerTokenErrors
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver
import org.springframework.security.web.util.matcher.AndRequestMatcher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.util.StringUtils
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.regex.Pattern


class CookieBearerTokenResolver : BearerTokenResolver {
    private val bearerTokenHeaderName = HttpHeaders.AUTHORIZATION

    private val authorizationPattern: Pattern = Pattern.compile(
        "^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
        Pattern.CASE_INSENSITIVE
    )
    val adminMatcher: RequestMatcher = AntPathRequestMatcher("/admin/**")
    val noLoginMatcher: RequestMatcher = NegatedRequestMatcher(AntPathRequestMatcher("/admin/login"))
    val matcher = AndRequestMatcher(adminMatcher, noLoginMatcher)
    private fun resolveFromAuthorizationHeader(request: HttpServletRequest): String? {
        val authorization = request.getHeader(this.bearerTokenHeaderName)
        return if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            null
        } else {
            val matcher = authorizationPattern.matcher(authorization)
            if (!matcher.matches()) {
                return null
            } else {
                matcher.group("token")
            }
        }
    }

    override fun resolve(request: HttpServletRequest): String? {
        if (!matcher.matches(request)) {
            return null
        }
        val authorizationHeaderToken = this.resolveFromAuthorizationHeader(request)
        if (authorizationHeaderToken != null) {
            return authorizationHeaderToken
        }
        request.cookies?.forEach { cookie ->
            if (cookie.name == bearerTokenHeaderName) {
                val token = URLDecoder.decode(cookie.value, StandardCharsets.UTF_8.name())
                val matcher = authorizationPattern.matcher(token)
                if (!matcher.matches()) {
                    return null
                }
                return matcher.group("token")
            }
        }
        return null
    }
}
package com.shch.a4blog.handler

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver
import java.util.regex.Pattern

class CookieBearerTokenResolver : BearerTokenResolver {
    private val bearerTokenHeaderName = HttpHeaders.AUTHORIZATION

    private val authorizationPattern: Pattern = Pattern.compile(
        "^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
        Pattern.CASE_INSENSITIVE
    )

    override fun resolve(request: HttpServletRequest): String? {
        request.cookies?.forEach { cookie ->
            if (cookie.name == bearerTokenHeaderName) {
                val token = cookie.value
                val matcher = authorizationPattern.matcher(token)
                if (!matcher.matches()) {
                    return null
                }
                return token
            }
        }
        return null
    }
}
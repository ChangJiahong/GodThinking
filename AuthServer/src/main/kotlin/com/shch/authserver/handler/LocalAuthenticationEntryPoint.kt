package com.shch.authserver.handler

import com.shch.authserver.error.AuthError
import com.shch.starterwebext.model.vm.error.toRest
import com.shch.starterwebext.utils.print
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class LocalAuthenticationEntryPoint: AuthenticationEntryPoint {
    private val accessTokenHttpResponseConverter = MappingJackson2HttpMessageConverter();

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        response.print(AuthError.NotLoggedIn.toRest());
    }
}
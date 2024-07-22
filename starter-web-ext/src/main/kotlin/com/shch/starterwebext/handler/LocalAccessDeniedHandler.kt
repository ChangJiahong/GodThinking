package com.shch.starterwebext.handler

import com.shch.starterwebext.error.AuthError
import com.shch.starterwebext.model.vm.error.toRest
import com.shch.starterwebext.utils.print
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class LocalAccessDeniedHandler : AccessDeniedHandler {
    private val accessTokenHttpResponseConverter = MappingJackson2HttpMessageConverter();

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.print(AuthError.NoPermission.toRest());
    }
}
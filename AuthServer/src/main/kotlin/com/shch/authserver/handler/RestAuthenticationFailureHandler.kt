package com.shch.authserver.handler

import com.shch.authserver.error.AuthError
import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.Rest.R.failed
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler

class RestAuthenticationFailureHandler: AuthenticationFailureHandler {

    /**
     * MappingJackson2HttpMessageConverter 是 Spring 框架提供的一个 HTTP 消息转换器，用于将 HTTP 请求和响应的 JSON 数据与 Java 对象之间进行转换
     */
    private val accessTokenHttpResponseConverter = MappingJackson2HttpMessageConverter();


    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        val error = ( exception as OAuth2AuthenticationException).error;
        val httpResponse = ServletServerHttpResponse(response);
        val result=Rest.failed(AuthError.OAuthError(error.errorCode))
        accessTokenHttpResponseConverter.write(result, null, httpResponse);
    }
}
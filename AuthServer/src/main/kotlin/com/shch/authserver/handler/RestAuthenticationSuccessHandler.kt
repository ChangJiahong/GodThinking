package com.shch.authserver.handler

import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.Rest.R.ok
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.endpoint.DefaultOAuth2AccessTokenResponseMapConverter
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.util.CollectionUtils
import java.time.temporal.ChronoUnit


class RestAuthenticationSuccessHandler: AuthenticationSuccessHandler {

    /** * MappingJackson2HttpMessageConverter 是 Spring 框架提供的一个 HTTP 消息转换器，用于将 HTTP 请求和响应的 JSON 数据与 Java 对象之间进行转换  */
    private val accessTokenHttpResponseConverter=
        MappingJackson2HttpMessageConverter()

    private var accessTokenResponseParametersConverter = DefaultOAuth2AccessTokenResponseMapConverter()

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse,
                                         authentication: Authentication) {
        val accessTokenAuthentication = authentication as OAuth2AccessTokenAuthenticationToken
        val accessToken: OAuth2AccessToken =
            accessTokenAuthentication.accessToken
        val refreshToken = accessTokenAuthentication.refreshToken
        val additionalParameters = accessTokenAuthentication.additionalParameters

        val builder = OAuth2AccessTokenResponse.withToken(accessToken.tokenValue)
            .tokenType(accessToken.tokenType)

        if (accessToken.issuedAt != null && accessToken.expiresAt != null) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.issuedAt, accessToken.expiresAt))
        }
        if (refreshToken != null) {
            builder.refreshToken(refreshToken.tokenValue)
        }
        if (!CollectionUtils.isEmpty(additionalParameters)) {
            builder.additionalParameters(additionalParameters)
        }
        val accessTokenResponse = builder.build()
        val tokenResponseParameters=
            accessTokenResponseParametersConverter.convert(accessTokenResponse)

        val httpResponse = ServletServerHttpResponse(response)
        this.accessTokenHttpResponseConverter.write(
            Rest.ok(tokenResponseParameters),
            null,
            httpResponse
        )

    }
}
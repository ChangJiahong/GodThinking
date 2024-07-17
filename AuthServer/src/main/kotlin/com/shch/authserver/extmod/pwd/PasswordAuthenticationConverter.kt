package com.shch.authserver.extmod.pwd

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2ErrorCodes
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.web.authentication.AuthenticationConverter
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.util.StringUtils
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors


/**
 * 密码模式参数解析器
 *
 *
 * 解析请求参数中的用户名和密码，并构建相应的身份验证(Authentication)对象
 *
 * @author haoxr
 * @see org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter
 *
 * @since 3.0.0
 */
class PasswordAuthenticationConverter : AuthenticationConverter {

    val ACCESS_TOKEN_REQUEST_ERROR_URI: String = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2"

    fun getParameters(request: HttpServletRequest): MultiValueMap<String, String> {
        val parameterMap = request.parameterMap
        val parameters: MultiValueMap<String, String> = LinkedMultiValueMap(parameterMap.size)
        parameterMap.forEach { (key: String, values: Array<String>) ->
            if (values.isNotEmpty()) {
                for (value in values) {
                    parameters.add(key, value)
                }
            }
        }
        return parameters
    }

    fun throwError(errorCode: String, parameterName: String, errorUri: String) {
        val error = OAuth2Error(errorCode, "OAuth 2.0 Parameter: $parameterName", errorUri)
        throw OAuth2AuthenticationException(error)
    }

    override fun convert(request: HttpServletRequest): Authentication? {
        // 授权类型 (必需)

        val grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE)
        if (AuthorizationGrantType.PASSWORD.value != grantType) {
            return null
        }

        // 客户端信息
        val clientPrincipal = SecurityContextHolder.getContext().authentication

        // 参数提取验证
        val parameters: MultiValueMap<String, String> = getParameters(request)

        // 令牌申请访问范围验证 (可选)
        val scope = parameters.getFirst(OAuth2ParameterNames.SCOPE)
        if (StringUtils.hasText(scope) &&
            parameters[OAuth2ParameterNames.SCOPE]!!.size != 1
        ) {
            throwError(
                OAuth2ErrorCodes.INVALID_REQUEST,
                OAuth2ParameterNames.SCOPE,
                ACCESS_TOKEN_REQUEST_ERROR_URI
            )
        }
        var requestedScopes: Set<String>? = null
        if (StringUtils.hasText(scope)) {
            requestedScopes = HashSet(Arrays.asList(*StringUtils.delimitedListToStringArray(scope, " ")))
        }

        // 用户名验证(必需)
        val username = parameters.getFirst(OAuth2ParameterNames.USERNAME)
        if (io.micrometer.common.util.StringUtils.isBlank(username)) {
            throwError(
                OAuth2ErrorCodes.INVALID_REQUEST,
                OAuth2ParameterNames.USERNAME,
                ACCESS_TOKEN_REQUEST_ERROR_URI
            )
        }

        // 密码验证(必需)
        val password = parameters.getFirst(OAuth2ParameterNames.PASSWORD)
        if (io.micrometer.common.util.StringUtils.isBlank(password)) {
            throwError(
                OAuth2ErrorCodes.INVALID_REQUEST,
                OAuth2ParameterNames.PASSWORD,
                ACCESS_TOKEN_REQUEST_ERROR_URI
            )
        }

        // 附加参数(保存用户名/密码传递给 PasswordAuthenticationProvider 用于身份认证)
        val additionalParameters = parameters
            .entries
            .stream()
            .filter { e: Map.Entry<String, List<String>> ->
                e.key != OAuth2ParameterNames.GRANT_TYPE &&
                        e.key != OAuth2ParameterNames.SCOPE
            }.collect(
                Collectors.toMap(Map.Entry<String,List<String>>::key) { e -> e.value[0] }
//                Collectors.toMap<Map.Entry<String, List<String>>, String, Any>(
//                    Function<Map.Entry<String, List<String>>, String> { java.util.Map.Entry.key },
//                    Function<Map.Entry<String, List<String>>, Any> { e: Map.Entry<String, List<String>> -> e.value[0] })
            )

        return PasswordAuthenticationToken(
            clientPrincipal,
            requestedScopes,
            additionalParameters
        )
    }
}

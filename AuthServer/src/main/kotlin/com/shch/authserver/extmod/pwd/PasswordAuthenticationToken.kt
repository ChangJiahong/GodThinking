package com.shch.authserver.extmod.pwd

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken
import java.util.*
import kotlin.collections.HashSet

/**
 * 密码授权模式身份验证令牌(包含用户名和密码等)
 *
 * @author haoxr
 * @since 3.0.0
 */
class PasswordAuthenticationToken(
    clientPrincipal: Authentication,
    scopes: Set<String>?,
    additionalParameters: Map<String, Any>
) : OAuth2AuthorizationGrantAuthenticationToken(PASSWORD, clientPrincipal, additionalParameters) {
    /**
     * 令牌申请访问范围
     */
    val scopes =
        Collections.unmodifiableSet(if (scopes != null) HashSet(scopes) else Collections.emptySet())

    /**
     * 用户凭证(密码)
     */
    override fun getCredentials(): Any {

        return additionalParameters.get(OAuth2ParameterNames.PASSWORD)!!
    }


    companion object {
        val PASSWORD: AuthorizationGrantType = AuthorizationGrantType("password")
    }
}

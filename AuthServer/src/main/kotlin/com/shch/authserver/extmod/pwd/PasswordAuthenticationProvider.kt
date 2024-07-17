package com.shch.authserver.extmod.pwd

import com.shch.authserver.extmod.pwd.PasswordAuthenticationToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.*
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator
import org.springframework.util.CollectionUtils
import java.security.Principal
import java.util.*
import java.util.stream.Collectors


/**
 * 密码模式身份验证提供者
 *
 *
 * 处理基于用户名和密码的身份验证
 *
 * @author haoxr
 * @since 3.0.0
 */
class PasswordAuthenticationProvider(
    private val authenticationManager: AuthenticationManager,
    private val authorizationService: OAuth2AuthorizationService,
    private val tokenGenerator: OAuth2TokenGenerator<out OAuth2Token>
) : AuthenticationProvider {

    private fun getAuthenticatedClientElseThrowInvalidClient(authentication: Authentication): OAuth2ClientAuthenticationToken {
        var clientPrincipal: OAuth2ClientAuthenticationToken? = null
        if (OAuth2ClientAuthenticationToken::class.java.isAssignableFrom(authentication.principal.javaClass)) {
            clientPrincipal = authentication.principal as OAuth2ClientAuthenticationToken
        }
        if (clientPrincipal != null && clientPrincipal.isAuthenticated) {
            return clientPrincipal
        }
        throw OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT)
    }

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val passwordAuthenticationToken = authentication as PasswordAuthenticationToken
        val clientPrincipal: OAuth2ClientAuthenticationToken = getAuthenticatedClientElseThrowInvalidClient(passwordAuthenticationToken)
        val registeredClient = clientPrincipal.registeredClient

        // 验证客户端是否支持授权类型(grant_type=password)
        if (!registeredClient!!.authorizationGrantTypes.contains(AuthorizationGrantType.PASSWORD)) {
            throw OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT)
        }

        var additionalParameters = passwordAuthenticationToken.additionalParameters


        // 生成用户名密码身份验证令牌
        val username = additionalParameters[OAuth2ParameterNames.USERNAME] as String?
        val password = additionalParameters[OAuth2ParameterNames.PASSWORD] as String?

        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(username, password)

        // 用户名密码身份验证，成功后返回带有权限的认证信息
        val usernamePasswordAuthentication: Authentication
        try {
            usernamePasswordAuthentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken)
        } catch (e: Exception) {
            // 需要将其他类型的异常转换为 OAuth2AuthenticationException 才能被自定义异常捕获处理，逻辑源码 OAuth2TokenEndpointFilter#doFilterInternal
            throw OAuth2AuthenticationException(if (e.cause != null) e.cause!!.message else e.message)
        }

        // 验证申请访问范围(Scope)
        var authorizedScopes = registeredClient.scopes
        val requestedScopes = passwordAuthenticationToken.scopes
        if (!CollectionUtils.isEmpty(requestedScopes)) {
            val unauthorizedScopes = requestedScopes.stream()
                .filter { requestedScope: String? -> !registeredClient.scopes.contains(requestedScope) }
                .collect(Collectors.toSet())
            if (!CollectionUtils.isEmpty(unauthorizedScopes)) {
                throw OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE)
            }
            authorizedScopes = LinkedHashSet(requestedScopes)
        }

        // 访问令牌(Access Token) 构造器
        val tokenContextBuilder = DefaultOAuth2TokenContext.builder()
            .registeredClient(registeredClient)
            .principal(usernamePasswordAuthentication) // 身份验证成功的认证信息(用户名、权限等信息)
            .authorizationServerContext(AuthorizationServerContextHolder.getContext())
            .authorizedScopes(authorizedScopes)
            .authorizationGrantType(AuthorizationGrantType.PASSWORD) // 授权方式
            .authorizationGrant(passwordAuthenticationToken)
        // 授权具体对象


        // 生成访问令牌(Access Token)
        var tokenContext: OAuth2TokenContext = tokenContextBuilder.tokenType((OAuth2TokenType.ACCESS_TOKEN)).build()
        val generatedAccessToken = tokenGenerator.generate(tokenContext)
        if (generatedAccessToken == null) {
            val error = OAuth2Error(
                OAuth2ErrorCodes.SERVER_ERROR,
                "The token generator failed to generate the access token.", ERROR_URI
            )
            throw OAuth2AuthenticationException(error)
        }


        val accessToken = OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            generatedAccessToken.tokenValue, generatedAccessToken.issuedAt,
            generatedAccessToken.expiresAt, tokenContext.authorizedScopes
        )

        val authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
            .principalName(usernamePasswordAuthentication.name)
            .authorizationGrantType(AuthorizationGrantType.PASSWORD)
            .authorizedScopes(authorizedScopes)
            .attribute(Principal::class.java.name, usernamePasswordAuthentication) // attribute 字段
        if (generatedAccessToken is ClaimAccessor) {
            authorizationBuilder.token(accessToken) { metadata: MutableMap<String?, Any?> ->
                metadata[OAuth2Authorization.Token.CLAIMS_METADATA_NAME] =
                    (generatedAccessToken as ClaimAccessor).claims
            }
        } else {
            authorizationBuilder.accessToken(accessToken)
        }

        // 生成刷新令牌(Refresh Token)
        var refreshToken: OAuth2RefreshToken? = null
        if (registeredClient.authorizationGrantTypes.contains(AuthorizationGrantType.REFRESH_TOKEN) &&  // Do not issue refresh token to public client
            clientPrincipal.clientAuthenticationMethod != ClientAuthenticationMethod.NONE
        ) {
            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build()
            val generatedRefreshToken = tokenGenerator.generate(tokenContext)
            if (generatedRefreshToken !is OAuth2RefreshToken) {
                val error = OAuth2Error(
                    OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the refresh token.", ERROR_URI
                )
                throw OAuth2AuthenticationException(error)
            }

            refreshToken = generatedRefreshToken
            authorizationBuilder.refreshToken(refreshToken)
        }

        // ----- ID token -----
        val idToken: OidcIdToken?
        if (requestedScopes.contains(OidcScopes.OPENID)) {
            // @formatter:off
            tokenContext = tokenContextBuilder
            .tokenType(ID_TOKEN_TOKEN_TYPE)
            .authorization(authorizationBuilder.build()) // ID token customizer may need access to the access token and/or refresh token
            .build()
                        // @formatter:on
            val generatedIdToken = tokenGenerator.generate(tokenContext)
            if (generatedIdToken !is Jwt) {
                val error = OAuth2Error(
                    OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the ID token.", ERROR_URI
                )
                throw OAuth2AuthenticationException(error)
            }

//            if (log.isTraceEnabled()) {
//                log.trace("Generated id token")
//            }

            idToken = OidcIdToken(
                generatedIdToken.getTokenValue(), generatedIdToken.getIssuedAt(),
                generatedIdToken.getExpiresAt(), generatedIdToken.claims
            )
            authorizationBuilder.token(idToken) { metadata: MutableMap<String?, Any?> ->
                metadata[OAuth2Authorization.Token.CLAIMS_METADATA_NAME] = idToken.claims
            }
        } else {
            idToken = null
        }

        // 持久化令牌发放记录到数据库
        val authorization = authorizationBuilder.build()
        authorizationService.save(authorization)

        additionalParameters = if ((idToken != null)
        ) Collections.singletonMap<String?, Any?>(OidcParameterNames.ID_TOKEN, idToken.tokenValue)
        else emptyMap()

        return OAuth2AccessTokenAuthenticationToken(
            registeredClient,
            clientPrincipal,
            accessToken,
            refreshToken,
            additionalParameters
        )
    }

    /**
     * 判断传入的 authentication 类型是否与当前认证提供者(AuthenticationProvider)相匹配--模板方法
     *
     *
     * ProviderManager#authenticate 遍历 providers 找到支持对应认证请求的 provider-迭代器模式
     *
     * @param authentication
     * @return
     */
    override fun supports(authentication: Class<*>): Boolean {
        return PasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

    companion object {
        private const val ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2"
        private val ID_TOKEN_TOKEN_TYPE = OAuth2TokenType(OidcParameterNames.ID_TOKEN)
    }
}
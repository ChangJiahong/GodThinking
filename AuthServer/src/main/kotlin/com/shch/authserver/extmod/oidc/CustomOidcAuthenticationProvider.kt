package com.shch.authserver.extmod.oidc

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken
import org.springframework.util.Assert
import java.util.function.Function
import java.util.function.Predicate


/**
 * 自定义 OIDC 认证提供者
 *
 * @author Ray Hao
 * @since 3.1.0
 */
class CustomOidcAuthenticationProvider(authorizationService: OAuth2AuthorizationService) : AuthenticationProvider {
    private val authorizationService: OAuth2AuthorizationService

    init {
        Assert.notNull(authorizationService, "authorizationService cannot be null")
        this.authorizationService = authorizationService
    }

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val userInfoAuthentication = authentication as OidcUserInfoAuthenticationToken
        var accessTokenAuthentication: AbstractOAuth2TokenAuthenticationToken<*>? = null
        if (AbstractOAuth2TokenAuthenticationToken::class.java.isAssignableFrom(userInfoAuthentication.principal.javaClass)) {
            accessTokenAuthentication = userInfoAuthentication.principal as AbstractOAuth2TokenAuthenticationToken<*>
        }

        if (accessTokenAuthentication != null && accessTokenAuthentication.isAuthenticated) {
            val accessTokenValue = accessTokenAuthentication.token.tokenValue
            val authorization = authorizationService.findByToken(accessTokenValue, OAuth2TokenType.ACCESS_TOKEN)
            if (authorization == null) {
                throw OAuth2AuthenticationException("invalid_token")
            } else {
//                if (log.isTraceEnabled()) {
//                    log.trace("Retrieved authorization with access token");
//                }

                val authorizedAccessToken = authorization.accessToken
                if (!authorizedAccessToken.isActive) {
                    throw OAuth2AuthenticationException("invalid_token")
                } else {
                    // 从认证结果中获取userInfo
                    val customOidcUserInfo = userInfoAuthentication.userInfo as CustomOidcUserInfo
                    // 从authorizedAccessToken中获取授权范围
                    val scopeSet: Set<String>? = authorizedAccessToken.claims!!["scope"] as HashSet<String>?
                    // 获取授权范围对应userInfo的字段信息
                    val claims =
                        DefaultOidcUserInfoMapper.getClaimsRequestedByScope(customOidcUserInfo.claims, scopeSet)

                    //                    if (log.isTraceEnabled()) {
//                        log.trace("Authenticated user info request");
//                    }
                    return CustomOidcToken(accessTokenAuthentication, CustomOidcUserInfo(claims))
                }
            }
        } else {
            throw OAuth2AuthenticationException("invalid_token")
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return OidcUserInfoAuthenticationToken::class.java.isAssignableFrom(authentication)
    }


    private class DefaultOidcUserInfoMapper private constructor() :
        Function<OidcUserInfoAuthenticationContext, CustomOidcUserInfo> {
        override fun apply(authenticationContext: OidcUserInfoAuthenticationContext): CustomOidcUserInfo {
            val authorization = authenticationContext.authorization
            val idToken = authorization.getToken(OidcIdToken::class.java)!!.token
            val accessToken = authenticationContext.accessToken
            val scopeRequestedClaims = getClaimsRequestedByScope(idToken.claims, accessToken.scopes)
            return CustomOidcUserInfo(scopeRequestedClaims)
        }

        companion object {
            private val EMAIL_CLAIMS: List<String> = mutableListOf("email", "email_verified")
            private val PHONE_CLAIMS: List<String> = mutableListOf("phone_number", "phone_number_verified")
            private val PROFILE_CLAIMS: List<String> = mutableListOf("username", "nickname", "status", "profile")

            /**
             * 根据不同权限抽取不同数据
             */
            fun getClaimsRequestedByScope(claims: Map<String, Any>, requestedScopes: Set<String>?): Map<String, Any> {
                val scopeRequestedClaimNames: MutableSet<String> = HashSet(32)
                scopeRequestedClaimNames.add("sub")

                if (requestedScopes!!.contains("address")) {
                    scopeRequestedClaimNames.add("address")
                }

                if (requestedScopes.contains("email")) {
                    scopeRequestedClaimNames.addAll(EMAIL_CLAIMS)
                }

                if (requestedScopes.contains("phone")) {
                    scopeRequestedClaimNames.addAll(PHONE_CLAIMS)
                }

                if (requestedScopes.contains("profile")) {
                    scopeRequestedClaimNames.addAll(PROFILE_CLAIMS)
                }

                val requestedClaims: MutableMap<String, Any> = HashMap(claims)
                requestedClaims.keys.removeIf{ claimName: String ->
                    !scopeRequestedClaimNames.contains(
                        claimName
                    )
                }
                return requestedClaims
            }
        }
    }
}
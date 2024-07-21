package com.shch.resdemo.handler

import com.shch.resdemo.AuthError
import com.shch.starterwebext.model.vm.error.RestCode
import com.shch.starterwebext.model.vm.error.toRest
import com.shch.starterwebext.utils.print
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.security.authentication.*
import org.springframework.security.authentication.password.CompromisedPasswordException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException
import org.springframework.security.web.authentication.rememberme.*
import org.springframework.security.web.authentication.rememberme.InvalidCookieException
import org.springframework.security.web.authentication.www.NonceExpiredException

class LocalAuthenticationEntryPoint : AuthenticationEntryPoint {
    private val accessTokenHttpResponseConverter = MappingJackson2HttpMessageConverter();

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {

        val restCode = converterAuthExceptionToRestCode(authException)
        response.print(restCode.toRest());
    }


    private fun converterAuthExceptionToRestCode(authException: AuthenticationException?): RestCode {
        if (authException == null) {
            return AuthError.NotLoggedIn
        }
        return when (authException) {
            is AccountExpiredException -> AuthError.AccountExpired
            is AccountStatusException -> AuthError.AccountStatus
            is AuthenticationCredentialsNotFoundException -> AuthError.AuthenticationCredentialsNotFound
            is AuthenticationServiceException -> AuthError.AuthenticationService
            is BadCredentialsException -> AuthError.BadCredentials
            is CompromisedPasswordException -> AuthError.CompromisedPassword
            is CookieTheftException -> AuthError.CookieTheft
            is CredentialsExpiredException -> AuthError.CredentialsExpired
            is DisabledException -> AuthError.Disabled
            is InsufficientAuthenticationException -> AuthError.InsufficientAuthentication(authException.message?:"")
            is InternalAuthenticationServiceException -> AuthError.InternalAuthenticationService
            is InvalidBearerTokenException -> AuthError.InvalidBearerToken
            is InvalidCookieException -> AuthError.InvalidCookie
            is LockedException -> AuthError.Locked
            is NonceExpiredException -> AuthError.NonceExpired
            is OAuth2AuthenticationException -> AuthError.OAuth2Authentication
//            is OAuth2AuthorizationCodeRequestAuthenticationException -> AuthError.OAuth2AuthorizationCodeRequestAuthentication
            is PreAuthenticatedCredentialsNotFoundException -> AuthError.PreAuthenticatedCredentialsNotFound
            is ProviderNotFoundException -> AuthError.ProviderNotFound
            is RememberMeAuthenticationException -> AuthError.RememberMeAuthentication
            is UsernameNotFoundException -> AuthError.UsernameNotFound
            else -> AuthError.NotLoggedIn
        }
    }
}
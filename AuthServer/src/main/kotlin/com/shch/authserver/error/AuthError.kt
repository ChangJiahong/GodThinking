package com.shch.authserver.error

import com.shch.starterwebext.model.vm.error.RestCode


/**
 * 600-699
 */
sealed class AuthError (code: Int, vararg args: String) : RestCode(code, args) {
    class OAuthError(errorCode: String) : AuthError(600, errorCode)

    data object NoPermission : AuthError(601)
    data object NotLoggedIn : AuthError(602)


    data object AccountExpired : AuthError(603)
    data object AccountStatus : AuthError(604)

    data object AuthenticationCredentialsNotFound : AuthError(605)

    data object AuthenticationService : AuthError(606)

    data object BadCredentials : AuthError(607)

    data object CompromisedPassword : AuthError(608)

    data object CookieTheft : AuthError(609)

    data object CredentialsExpired : AuthError(610)

    data object Disabled : AuthError(611)

    data class InsufficientAuthentication(val msg:String) : AuthError(612,msg)

    data object InternalAuthenticationService : AuthError(613)

    data object InvalidBearerToken : AuthError(614)

    data object InvalidCookie: AuthError(615)

    data object Locked : AuthError(616)

    data object NonceExpired : AuthError(617)

    data object OAuth2Authentication:AuthError(618)

    data object OAuth2AuthorizationCodeRequestAuthentication:AuthError(619)

    data object PreAuthenticatedCredentialsNotFound:AuthError(620)

    data object ProviderNotFound:AuthError(621)

    data object RememberMeAuthentication:AuthError(622)

    data object UsernameNotFound:AuthError(623)
}


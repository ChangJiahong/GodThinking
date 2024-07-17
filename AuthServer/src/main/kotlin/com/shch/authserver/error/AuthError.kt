package com.shch.authserver.error

import com.shch.starterwebext.model.vm.error.RestCode


/**
 * 600-699
 */
sealed class AuthError (code: Int, vararg args: String) : RestCode(code, args){
    class OAuthError ( errorCode: String) : AuthError(600, errorCode)

    data object NoPermission:AuthError(601)
    data object NotLoggedIn:AuthError(602)
}


package com.shch.authserver.error

import com.shch.starterwebext.model.vm.error.RestCode

sealed class OauthError( code: Int, vararg args: String) :  RestCode(code, args)  {
    data object UserNotFound:OauthError(700)
}
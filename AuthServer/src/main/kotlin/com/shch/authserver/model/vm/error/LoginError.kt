package com.shch.authserver.model.vm.error

/**
 * 登录错误
 * @des 100-199
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
sealed class LoginError(code: Int, vararg args: String) : RestCode(code, args) {

     data object UsernameError : LoginError(100)

}
package com.shch.starterwebext.model.vm.error

/**
 * 系统错误
 * @des 500-599
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
sealed class SystemError(code: Int, vararg args: String) : RestCode(code, args) {

    data class ServerError(val msg:String): SystemError(500,msg)

    data class ErrorCodeUndefined(val ecode: String) : SystemError(501, ecode)

    /**
     * 服务器错误
     */
    data object InternalServerError: SystemError(502)



}

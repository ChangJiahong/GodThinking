package com.shch.authserver.model.vm.error

import com.shch.authserver.utils.getRestCodeMsg
import com.shch.authserver.model.vm.Rest
import com.shch.authserver.model.vm.Rest.R.parse
import com.shch.starterwebext.getBean
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException

/**
 *
 * @des
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
sealed class RestCode(
    val code: Int,
    val args: Array<out String> = arrayOf(),
    val codeId: String = "A$code"
) : RuntimeException(codeId)

fun RestCode.toPair(): Pair<Int, String> {
    val messageSource = getBean<MessageSource>()
    return try {
        messageSource.getRestCodeMsg(this)
    } catch (_: NoSuchMessageException) {
        val error = SystemError.ErrorCodeUndefined(code.toString())
        messageSource.getRestCodeMsg(error)
    }
}

fun RestCode.toRest() = Rest.parse(this)

data object OK : RestCode(200)
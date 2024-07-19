package com.shch.starterwebext.model.vm.error

import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.Rest.R.parse
import com.shch.starterwebext.getBean
import com.shch.starterwebext.utils.getRestCodeMsg
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException

/**
 *
 * @des
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
open class RestCode(
    val code: Int,
    val args: Array<out String> = arrayOf(),
    val codeId: String = "A$code"
) : RuntimeException(codeId)

fun RestCode.toPair(): Pair<Int, String> {
    val messageSource = getBean<MessageSource>()
    return try {
        messageSource.getRestCodeMsg(this)
    } catch (_: NoSuchMessageException) {
        val error = SystemError.ErrorCodeUndefined(
            this::class.java.simpleName)
        messageSource.getRestCodeMsg(error)
    }
}

fun RestCode.toRest() = Rest.parse(this)

data object OK : RestCode(200)
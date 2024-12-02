package com.shch.starterwebext.model.vm.error

import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.Rest.R.parse
import com.shch.starterwebext.getBean
import com.shch.starterwebext.utils.getRestCodeMsg
import org.slf4j.LoggerFactory
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

val restCodeLogger = LoggerFactory.getLogger(RestCode::class.java)

fun RestCode.toPair(): Pair<Int, String> {
    val messageSource = getBean<MessageSource>()
    return try {
        messageSource.getRestCodeMsg(this)
    } catch (_: NoSuchMessageException) {
        restCodeLogger.info("未定义代码({})：{} {}",this::class.java.simpleName,this.codeId, this.args)
        val error = SystemError.ErrorCodeUndefined(
            this::class.java.simpleName,this.codeId)
        messageSource.getRestCodeMsg(error,"ACode {0}({1}) is undefined in {2} language")
    }
}

fun RestCode.toRest() = Rest.parse(this)

data object OK : RestCode(200)
package com.shch.starterwebext.utils

import com.shch.starterwebext.model.vm.error.RestCode
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

val logger = LoggerFactory.getLogger("Ext")

/**
 *
 * @des
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
fun MessageSource.getRestCodeMsg(restcode: RestCode, defaultMessage: String? = null): Pair<Int, String> {
    val locale = LocaleContextHolder.getLocale()
    logger.info("locale: {}", locale)
    val msg: String = if (defaultMessage != null)
        getMessage(restcode.codeId, restcode.args, defaultMessage, locale)!!
    else getMessage(restcode.codeId, restcode.args, locale)
    return Pair(restcode.code, msg)
}

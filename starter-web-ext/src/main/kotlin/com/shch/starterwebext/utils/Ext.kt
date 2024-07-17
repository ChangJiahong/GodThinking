package com.shch.starterwebext.utils

import com.shch.starterwebext.model.vm.error.RestCode
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

/**
 *
 * @des
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
fun MessageSource.getRestCodeMsg(restcode: RestCode): Pair<Int, String> {
    val locale = LocaleContextHolder.getLocale()
    return Pair(restcode.code,getMessage(restcode.codeId, restcode.args, locale))
}

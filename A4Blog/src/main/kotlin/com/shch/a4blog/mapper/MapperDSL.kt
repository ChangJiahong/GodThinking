package com.shch.a4blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import java.io.Serializable
import java.util.*

interface IBaseMapper<T,M> :BaseMapper<T>{}


inline fun <reified T : Any> BaseMapper<T>.query(block: KtQueryChainWrapper<T>.() -> T): T {
    return block(query())
}

inline fun <reified T : Any> BaseMapper<T>.query(): KtQueryChainWrapper<T> {
    return KtQueryChainWrapper(this, T::class.java)
}

fun <T,M: IBaseMapper<T, M>> M.op(block: M.() -> T?): Optional<T & Any> {
    return Optional.ofNullable(block(this))
}

fun <T> BaseMapper<T>.selectOptById(id: Serializable): Optional<T & Any> {
    return Optional.ofNullable(selectById(id))
}

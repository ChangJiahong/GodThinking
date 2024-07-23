package com.shch.starterwebext.annotation

import kotlin.reflect.KClass


@Target(
    //类注解
    AnnotationTarget.CLASS,
//    //属性变量注解
//    AnnotationTarget.FIELD,
//    //函数方法注解
//    AnnotationTarget.FUNCTION,
//    //方法参数注解
//    AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.RUNTIME)
annotation class AutoMapper(val mapperClazz: KClass<*>)

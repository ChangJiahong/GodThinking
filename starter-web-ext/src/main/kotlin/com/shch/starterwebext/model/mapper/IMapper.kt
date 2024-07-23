package com.shch.starterwebext.model.mapper

import com.shch.starterwebext.annotation.AutoStruct
import com.shch.starterwebext.model.vm.error.SystemError
import org.mapstruct.factory.Mappers

inline fun <reified A, reified B> A.go(): B {
    val sourceClass = A::class.java
    val targetClass = B::class.java
    if (!sourceClass.isAnnotationPresent(AutoStruct::class.java)){
        throw SystemError.ServerError("class ${sourceClass.name} 未找到可以转换的Mapper")
    }
    val mapperClass = A::class.java.getAnnotation(AutoStruct::class.java).mapperClazz
    val mapper =  Mappers.getMapper(mapperClass.java)
    mapperClass.java.methods.forEach {method ->
        if (method.parameterCount!=1){
            return@forEach
        }
        val methodParamClass = method.parameterTypes[0]
        val methodReturnType = method.returnType
        if (methodParamClass.name==sourceClass.name
            && methodReturnType.name == targetClass.name) {
            return method.invoke(mapper,this) as B
        }
    }
    throw SystemError.ServerError("未找到可以转换的Mapper method")
}

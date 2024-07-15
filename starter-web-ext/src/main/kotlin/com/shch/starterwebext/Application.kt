package com.shch.starterwebext

import com.shch.starterwebext.Application.Companion.context
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext

open class Application {
    companion object {
        lateinit var context: ApplicationContext
    }
}

inline fun <reified T : Any> getBean(): T {
    return context.getBean()
}
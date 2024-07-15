package com.shch.authserver

import com.shch.authserver.AuthServerApplication.Companion.applicationContext
import org.springframework.beans.factory.getBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext

@SpringBootApplication
class AuthServerApplication{
    companion object {
        lateinit var applicationContext: ApplicationContext
    }
}

inline fun <reified T : Any> getBean(): T {
    return applicationContext.getBean()
}


fun main(args: Array<String>) {
    applicationContext = runApplication<AuthServerApplication>(*args)
}

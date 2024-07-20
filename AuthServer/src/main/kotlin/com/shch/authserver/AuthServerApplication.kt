package com.shch.authserver

import com.shch.starterwebext.Application
import com.shch.starterwebext.config.EnableWebExtConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

@SpringBootApplication
@EnableWebExtConfig
// 开启鉴权注解
@EnableMethodSecurity(securedEnabled = true,jsr250Enabled=true)
class AuthServerApplication:Application(){

}


fun main(args: Array<String>) {
    Application.context = runApplication<AuthServerApplication>(*args)
}

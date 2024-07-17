package com.shch.authserver

import com.shch.starterwebext.Application
import com.shch.starterwebext.config.EnableWebExtConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableWebExtConfig
class AuthServerApplication:Application(){

}


fun main(args: Array<String>) {
    Application.context = runApplication<AuthServerApplication>(*args)
}

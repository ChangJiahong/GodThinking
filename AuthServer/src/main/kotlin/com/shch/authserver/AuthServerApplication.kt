package com.shch.authserver

import com.shch.starterwebext.Application
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthServerApplication:Application(){

}


fun main(args: Array<String>) {
    Application.context = runApplication<AuthServerApplication>(*args)
}

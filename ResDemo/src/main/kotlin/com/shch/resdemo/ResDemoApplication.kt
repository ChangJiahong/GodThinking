                                                                                                                                                                                                                                                                                                                                                                                                 package com.shch.resdemo

import com.shch.starterwebext.Application
import com.shch.starterwebext.config.EnableWebExtConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableWebExtConfig
class ResDemoApplication: Application()


fun main(args: Array<String>) {
    Application.context = runApplication<ResDemoApplication>(*args)
}

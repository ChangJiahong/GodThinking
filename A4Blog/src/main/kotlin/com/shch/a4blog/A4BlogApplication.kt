package com.shch.a4blog

import com.shch.starterwebext.Application
import com.shch.starterwebext.config.EnableWebExtConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableWebExtConfig
class A4BlogApplication: Application()

fun main(args: Array<String>) {
    Application.context=runApplication<A4BlogApplication>(*args)
}

package com.shch.resdemo

import com.shch.starterwebext.Application
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class ResDemoApplication: Application()

@RestController
class H{

    @GetMapping("/hh")
    fun hel():String="SSSSS"
}

fun main(args: Array<String>) {
    runApplication<ResDemoApplication>(*args)
}

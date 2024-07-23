package com.shch.authserver

import com.shch.starterwebext.Application
import com.shch.starterwebext.config.EnableWebExtConfig
import org.apache.ibatis.annotations.Mapper
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableWebExtConfig
@MapperScan("com.shch.authserver.mapper", annotationClass = Mapper::class)
class AuthServerApplication:Application(){

}


fun main(args: Array<String>) {
    Application.context = runApplication<AuthServerApplication>(*args)
}

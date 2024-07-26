package com.shch.blogserver

import com.shch.starterwebext.Application
import com.shch.starterwebext.config.EnableWebExtConfig
import org.apache.ibatis.annotations.Mapper
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("com.shch.blogserver.mapper", annotationClass = Mapper::class)
@EnableWebExtConfig
class BlogServerApplication:Application()

fun main(args: Array<String>) {
	Application.context=runApplication<BlogServerApplication>(*args)
}

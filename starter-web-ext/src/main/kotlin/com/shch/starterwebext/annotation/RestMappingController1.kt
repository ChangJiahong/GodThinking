package com.shch.starterwebext.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@RestController
@RequestMapping
annotation class RestMappingController(
    @get:AliasFor(annotation = RequestMapping::class) vararg val value: String = [],
    @get:AliasFor(annotation = RequestMapping::class) val name: String = "",
    @get:AliasFor(annotation = RequestMapping::class) val path: Array<String> = [],
    @get:AliasFor(annotation = RequestMapping::class) val params: Array<String> = [],
    @get:AliasFor(annotation = RequestMapping::class) val headers: Array<String> = [],
    @get:AliasFor(annotation = RequestMapping::class) val consumes: Array<String> = [],
    @get:AliasFor(annotation = RequestMapping::class) val produces: Array<String> = []
)

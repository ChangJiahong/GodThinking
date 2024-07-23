//package com.shch.starterwebext.annotation
//
//import org.springframework.core.annotation.AliasFor
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//
//@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
//@Retention(AnnotationRetention.RUNTIME)
//@RestController
//annotation class RestMappingController(
//    @get:AliasFor(annotation = RequestMapping::class)
//    val name: String = "",
//    @get:AliasFor(annotation = RequestMapping::class)
//    val value: Array<String> = [],
//
//    @get:AliasFor(annotation = RequestMapping::class)
//    val path: Array<String> = [],
//
//    val params: Array<String> = [],
//
//    val headers: Array<String> = [],
//
//    val consumes: Array<String> = [],
//
//    val produces: Array<String> = [],
//)
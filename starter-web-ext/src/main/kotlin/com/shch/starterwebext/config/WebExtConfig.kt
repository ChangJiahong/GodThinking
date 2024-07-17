package com.shch.starterwebext.config

import com.shch.starterwebext.model.MessageSourceConfig
import org.springframework.context.MessageSource
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.support.AbstractResourceBasedMessageSource
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy


@Configuration
@ComponentScan(basePackages = ["com.shch.starterwebext"])
class WebExtConfig(messageSource: MessageSource,messageSourceConfig: MessageSourceConfig){

    init {
        messageSource as AbstractResourceBasedMessageSource
        messageSource.addBasenames(messageSourceConfig.basename)

    }
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@MustBeDocumented
@Import(
    WebExtConfig::class
)
annotation class EnableWebExtConfig

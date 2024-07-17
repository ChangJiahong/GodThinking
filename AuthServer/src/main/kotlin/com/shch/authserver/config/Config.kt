package com.shch.authserver.config

import com.shch.starterwebext.model.MessageSourceConfig
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.AbstractResourceBasedMessageSource
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver

/**
 *
 * @des
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
@Configuration
class AuthServerConfig (){

    @Bean
    fun getMessageSourceConfig():MessageSourceConfig{
        return MessageSourceConfig("i18n/messages")
    }

}
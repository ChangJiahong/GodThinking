package com.shch.a4blog.config

import com.shch.a4blog.api.IAuthApi
import com.shch.starterwebext.utils.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import java.util.Locale

@Configuration
class ApiClientConfig {

    @Value("\${com.shch.a4blog.authApiUrl}")
    lateinit var authApiUrl: String

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .baseUrl(authApiUrl)
            .defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, Locale.CHINESE.language) // 设置默认语言
            .filter { request, next ->
                // 每次请求时都根据当前语言环境动态设置 Accept-Language 头
                val locale = LocaleContextHolder.getLocale().language
                val mutatedRequest = ClientRequest.from(request)  // 使用 ClientRequest 构建新的请求
                    .header(HttpHeaders.ACCEPT_LANGUAGE, locale)  // 设置动态的 Accept-Language
                    .build()
                next.exchange(mutatedRequest)
            }
            .build()
    }

    @Bean
    fun authApi(webClient: WebClient): IAuthApi {
        val factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build()
        return factory.createClient(IAuthApi::class.java)
    }
}
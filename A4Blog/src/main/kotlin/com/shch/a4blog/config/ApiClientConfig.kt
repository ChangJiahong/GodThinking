package com.shch.a4blog.config

import com.shch.a4blog.api.IAuthApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class ApiClientConfig {
    @Bean
    fun webClient(): WebClient {
        return WebClient.create("http://localhost:8080")
    }

    @Bean
    fun authApi(webClient: WebClient): IAuthApi {
        val factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build()
        return factory.createClient(IAuthApi::class.java)
    }
}
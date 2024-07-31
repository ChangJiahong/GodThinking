package com.shch.a4blog.api

import com.shch.a4blog.model.vo.LoginResVO
import com.shch.starterwebext.model.vm.Rest
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange

@HttpExchange
interface IAuthApi {

    @PostExchange(value = "/oauth2/token")
    fun login(
        @RequestParam username: String,
        @RequestParam password: String,
        @RequestParam grant_type: String = "password",
        @RequestHeader("Authorization") authToken: String = "Basic QTQtQmxvZzpKeHhBNDIyMzM="
    ):Rest
}
package com.shch.a4blog.service

import com.shch.starterwebext.model.vm.Rest

interface IAccountService {
    fun login(username: String, password: String): Rest
}
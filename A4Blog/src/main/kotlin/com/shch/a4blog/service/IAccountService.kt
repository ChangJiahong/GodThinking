package com.shch.a4blog.service

import com.shch.a4blog.model.vm.RestRepo
import com.shch.a4blog.model.vo.LoginResVO
import com.shch.starterwebext.model.vm.Rest

interface IAccountService {
    fun login(username: String, password: String): RestRepo<LoginResVO>
}
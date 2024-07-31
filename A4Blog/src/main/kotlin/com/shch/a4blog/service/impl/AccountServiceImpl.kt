package com.shch.a4blog.service.impl

import com.shch.a4blog.api.IAuthApi
import com.shch.a4blog.model.vm.RestRepo
import com.shch.a4blog.model.vo.LoginResVO
import com.shch.a4blog.service.IAccountService
import com.shch.starterwebext.model.vm.Rest
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(val authApi: IAuthApi): IAccountService {

    override fun login(username: String, password: String): RestRepo<LoginResVO> {
        val rest = authApi.login(username, password)
        return rest
    }
}
package com.shch.authserver.service

import com.shch.authserver.model.bo.UserDetailsBO
import com.shch.authserver.model.vm.UserInfoVO

interface IUserService {
    fun getUserDetailsBOByEmail(email: String): UserDetailsBO

    fun getUserInfoVOByEmail(email: String): UserInfoVO
}
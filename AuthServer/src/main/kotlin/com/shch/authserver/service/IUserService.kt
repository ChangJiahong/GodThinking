package com.shch.authserver.service

import com.shch.authserver.model.bo.UserDetailsBO

interface IUserService {
    fun getUserDetailsBOByEmail(username: String): UserDetailsBO
}
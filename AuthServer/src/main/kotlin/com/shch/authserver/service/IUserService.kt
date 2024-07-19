package com.shch.authserver.service

import com.shch.authserver.model.po.UserDTO

interface IUserService {
    fun findByUsername(username: String): UserDTO
}
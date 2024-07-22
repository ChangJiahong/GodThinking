package com.shch.authserver.service

import com.shch.authserver.model.po.UserPO

interface IUserService {
    fun findByUsername(username: String): UserPO
}
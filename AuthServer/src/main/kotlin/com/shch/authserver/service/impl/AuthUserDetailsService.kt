package com.shch.authserver.service.impl

import com.shch.authserver.service.IUserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthUserDetailsService(val userService: IUserService): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userService.findByUsername(username)
    }
}
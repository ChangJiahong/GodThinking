package com.shch.authserver.service.impl

import com.shch.authserver.repository.UserRepository
import com.shch.authserver.service.IUserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository):IUserService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findById(1).get()
    }
}
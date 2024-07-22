package com.shch.authserver.service.impl

import com.shch.authserver.model.po.UserPO
import com.shch.authserver.repository.UserRepository
import com.shch.authserver.service.IUserService
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository):IUserService {

    override fun findByUsername(username: String): UserPO {
        return userRepository.findById(1).get()
    }
}
package com.shch.authserver.service.impl

import com.shch.authserver.model.bo.UserDetailsBO
import com.shch.authserver.model.po.UserPO
import com.shch.authserver.repository.UserRepository
import com.shch.authserver.service.IUserService
import com.shch.starterwebext.error.AuthError
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class UserService(val userRepository: UserRepository):IUserService {

    fun findPOByEmail(email: String): UserPO {
        val po=UserPO(email=email)
        val example = Example.of(po,ExampleMatcher.matching().withIgnorePaths(
            "id","uid","nickname","pwd","createTime"))

        return userRepository.findOne(example).getOrElse {
            throw AuthError.UsernameNotFound
        }
    }
    override fun getUserDetailsBOByEmail(email: String): UserDetailsBO {
        val userPO = findPOByEmail(email)
        return UserDetailsBO(userPO)
    }
}
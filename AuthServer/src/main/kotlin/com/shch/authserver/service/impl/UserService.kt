package com.shch.authserver.service.impl

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.shch.authserver.mapper.GtUserMapper
import com.shch.authserver.mapper.op
import com.shch.authserver.mapper.query
import com.shch.authserver.mapper.selectOptById
import com.shch.authserver.model.bo.UserDetailsBO
import com.shch.authserver.model.domain.GtUser
import com.shch.authserver.model.po.UserPO
import com.shch.authserver.repository.UserRepository
import com.shch.authserver.service.IUserService
import com.shch.starterwebext.error.AuthError
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class UserService(val userRepository: UserRepository, val gtUserMapper: GtUserMapper) : IUserService {

    fun findPOByEmail(email: String): GtUser {

        val user = gtUserMapper.query {
            eq(GtUser::email, email)
                .oneOpt()
                .getOrElse { throw AuthError.UsernameNotFound }
        }

        return user

//        val gtUser = gtUserMapper.selectOptById(2).getOrElse {
//            throw AuthError.UsernameNotFound
//        }

//        return userRepository.findOne(example).getOrElse {
//            throw AuthError.UsernameNotFound
//        }
    }

    override fun getUserDetailsBOByEmail(email: String): UserDetailsBO {
        val userPO = findPOByEmail(email)
        return UserDetailsBO(userPO)
    }
}
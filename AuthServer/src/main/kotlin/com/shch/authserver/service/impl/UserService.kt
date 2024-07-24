package com.shch.authserver.service.impl

import com.shch.authserver.mapper.GtUserMapper
import com.shch.authserver.mapper.op
import com.shch.authserver.mapper.query
import com.shch.authserver.model.bo.UserDetailsBO
import com.shch.authserver.model.domain.GtUser
import com.shch.authserver.model.vm.UserInfoVO
import com.shch.authserver.service.IUserService
import com.shch.starterwebext.error.AuthError
import com.shch.starterwebext.model.mapper.go
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class UserService(val gtUserMapper: GtUserMapper) : IUserService {

    fun findPOByEmail(email: String): GtUser {
        val gtUser = gtUserMapper
            .op { selectUserByEmail(email) }
            .getOrElse { throw AuthError.UsernameNotFound }
        return gtUser
    }

    /**
     * 去除pwd，roles敏感信息
     */
    fun findPOByEmailLess(email: String): GtUser {
        return gtUserMapper.query {
            select(GtUser::class.java) {
                it.property != GtUser::pwd.name
            }
                .eq(GtUser::email, email)
                .oneOpt().getOrElse { throw AuthError.UsernameNotFound }
        }
    }

    override fun getUserDetailsBOByEmail(email: String): UserDetailsBO {
        val userPO = findPOByEmail(email)
        return UserDetailsBO(userPO)
    }

    override fun getUserInfoVOByEmail(email: String): UserInfoVO {
        val gtUser = findPOByEmailLess(email)
        return gtUser.go()
    }
}
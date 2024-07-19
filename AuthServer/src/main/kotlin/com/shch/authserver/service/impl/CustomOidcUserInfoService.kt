package com.shch.authserver.service.impl

import com.shch.authserver.extmod.oidc.CustomOidcUserInfo
import com.shch.authserver.model.po.UserDTO
import com.shch.authserver.service.IUserService
import org.springframework.stereotype.Service


/**
 * 自定义 OIDC 用户信息服务
 *
 * @author Ray Hao
 * @since 3.1.0
 */
@Service
class CustomOidcUserInfoService(val userService: IUserService) {

    fun loadUserByUsername(username: String): CustomOidcUserInfo {

        val userAuthInfo = userService.findByUsername(username)
        return CustomOidcUserInfo(createUser(userAuthInfo))

    }

    private fun createUser(userAuthInfo: UserDTO): Map<String, Any> {
        return CustomOidcUserInfo.customBuilder()
//            .username(userAuthInfo.getUsername())
            .nickname(userAuthInfo.nickname)
//            .status(userAuthInfo.getStatus())
//            .phoneNumber(userAuthInfo.getMobile())
            .email(userAuthInfo.email)
//            .profile(userAuthInfo.getAvatar())
            .build()
            .claims
    }
}

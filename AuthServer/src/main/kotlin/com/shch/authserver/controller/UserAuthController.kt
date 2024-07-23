package com.shch.authserver.controller

import com.shch.authserver.model.mapper.UserMapper
import com.shch.authserver.model.po.UserPO
import com.shch.authserver.model.vm.UserInfoVM
import com.shch.authserver.service.IUserService
import com.shch.starterwebext.annotation.RestMappingController
import com.shch.starterwebext.model.mapper.go
import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.Rest.R.ok
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal

@RestMappingController("/api/v1/user")
class UserAuthController(val userService: IUserService, val userMapper: UserMapper) {
    var logger: Logger = LoggerFactory.getLogger(UserAuthController::class.java)


    @GetMapping("/info")
    fun userinfo(principal: Principal): Rest {
        val email = principal.name

//        userService.getUserDetailsBOByEmail()

        val userPO = UserPO(id = 10, nickname = "nicknick")

        val userinfo: UserInfoVM = userPO.go()

        val user: UserPO = userinfo.go()

        return Rest.ok(principal)
    }
}

package com.shch.authserver

import com.shch.starterwebext.model.vm.Rest.R.ok
import com.shch.authserver.service.ITestService
import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.error.SystemError
import jakarta.annotation.security.RolesAllowed
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @des
 * @author ChangJiahong
 * @date 2024/7/10
 * Create By IDEA
 */

@RestController
class Hello(val iTestService: ITestService) {
    @GetMapping("/hello")
    fun hello(): Rest {
        throw SystemError.InternalServerError

//        return Rest.ok(iTestService.test())
    }

    @GetMapping("/auth_p")
    fun auth():Rest{
        return Rest.ok("auth_p")
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/admin")
    fun hasAdmin():Rest{
        return Rest.ok("admin")
    }
}


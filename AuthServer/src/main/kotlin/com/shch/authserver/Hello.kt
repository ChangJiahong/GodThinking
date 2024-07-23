package com.shch.authserver

import com.shch.starterwebext.model.vm.Rest.R.ok
import com.shch.authserver.service.ITestService
import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.error.SystemError
import jakarta.annotation.security.RolesAllowed
import org.springframework.security.access.prepost.PreAuthorize
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
class Hello() {
    @GetMapping("/hello")
    fun hello(): Rest {
        throw SystemError.InternalServerError

//        return Rest.ok(iTestService.test())
    }

    @GetMapping("/auth_p")
    fun auth():Rest{
        return Rest.ok("auth_p")
    }

//    @RolesAllowed("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    fun hasAdmin():Rest{
        return Rest.ok("admin")
    }

        @RolesAllowed("ROLE_ADMIN")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin1")
    fun hasAdmin1():Rest{
        return Rest.ok("admin1")
    }

    @RolesAllowed("ADMIN")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin2")
    fun hasAdmin2():Rest{
        return Rest.ok("admin2")
    }

//    @RolesAllowed("ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin3")
    fun hasAdmin3():Rest{
        return Rest.ok("admin3")
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin4")
    fun hasAdmin4():Rest{
        return Rest.ok("admin4")
    }


    @RolesAllowed("ROLE_USER")
    @GetMapping("/user")
    fun user():Rest{
        return Rest.ok("user")
    }

    @PreAuthorize("hasAuthority('SCOPE_profile')")
    @GetMapping("/profile")
    fun profile():Rest{
        return Rest.ok("profile")
    }

    @PreAuthorize("hasAuthority('SCOPE_email')")
    @GetMapping("/email")
    fun email():Rest{
        return Rest.ok("email")
    }
}


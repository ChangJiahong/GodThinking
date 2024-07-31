package com.shch.a4blog.web

import com.shch.a4blog.service.IAccountService
import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.Rest.R.ok
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.security.Principal


@Controller
@RequestMapping("/admin")
class AdminController(val accountService:IAccountService) {

    @GetMapping("","/")
    fun adminIndex(principal: Principal): String {
        val username = principal.name
        return "admin/majestic/index"
    }

    @GetMapping("/login")
    fun login(): String {
        return "admin/majestic/login"
    }

    @PostMapping("/login")
    @ResponseBody
    fun doLogin(@RequestParam username: String, @RequestParam password: String,httpResponse: HttpServletResponse): Rest {

        val rest = accountService.login(username, password)

//        if (rest.isOk){
//            httpResponse.setHeader("Set-Cookie","Authorization=;refresh_token")
//        }
        return rest
    }

    @GetMapping("te")
    fun test(model: Model): String {
        model.addAttribute("text", "COnONCoNNNNN")
        return "admin/majestic/text"
    }

    @GetMapping("/sa")
    fun ta():Rest{
        return Rest.ok()
    }
}
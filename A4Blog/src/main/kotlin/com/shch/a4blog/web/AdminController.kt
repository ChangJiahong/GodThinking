package com.shch.a4blog.web

import com.shch.a4blog.service.IAccountService
import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.Rest.R.ok
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.Principal


@Controller
@RequestMapping("/admin")
class AdminController(val accountService: IAccountService) {

    @GetMapping("", "/")
    fun adminIndex(principal: Principal): String {
        val username = principal.name
        return "admin/majestic/index"
    }

    @GetMapping("edit-md")
    fun editMd():String{
        return "admin/majestic/pages/md/edit-md"
    }

    @GetMapping("manger-md")
    fun mangerMd():String{
        return "admin/majestic/pages/md/manger-md"
    }

    @GetMapping("/login")
    fun login(): String {
        return "admin/majestic/pages/samples/login"
    }

    @PostMapping("/login")
    @ResponseBody
    fun doLogin(
        @RequestParam username: String,
        @RequestParam password: String,
        httpResponse: HttpServletResponse
    ): Rest {
        val rest = accountService.login(username, password)
        if (rest.isOk) {
            val encodeToekn = URLEncoder.encode("bearer ${rest.data!!.access_token}", StandardCharsets.UTF_8.name())
            httpResponse.setHeader(
                "Set-Cookie",
                "Authorization=${encodeToekn};refresh_token=${rest.data.refresh_token}"
            )
        }
        return rest
    }

    @PostMapping("/put-md")
    @ResponseBody
    fun putMd(@RequestParam title:String,@RequestParam mdContent:String):Rest{
        return Rest.ok()
    }

    @GetMapping("te")
    fun test(model: Model): String {
        model.addAttribute("text", "COnONCoNNNNN")
        return "admin/majestic/text"
    }

    @GetMapping("/sa")
    fun ta(): Rest {
        return Rest.ok()
    }
}
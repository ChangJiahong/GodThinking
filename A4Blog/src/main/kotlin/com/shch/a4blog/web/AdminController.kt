package com.shch.a4blog.web

import com.shch.a4blog.model.bo.PageParams
import com.shch.a4blog.model.domain.GtMd
import com.shch.a4blog.service.IAccountService
import com.shch.a4blog.service.IMdService
import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.Rest.R.failed
import com.shch.starterwebext.model.vm.Rest.R.ok
import com.shch.starterwebext.model.vm.error.SystemError
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.Principal


@Controller
@RequestMapping("/admin")
class AdminController(val accountService: IAccountService, val mdService: IMdService) {

    @GetMapping("", "/")
    fun adminIndex(principal: Principal): String {
        val username = principal.name
        return "admin/majestic/index"
    }

    @GetMapping("edit-md")
    fun editMd(mdId: String?, model: Model): String {
        if (!mdId.isNullOrBlank()) {
            val md = mdService.findVOByMdId(mdId)
            model.addAttribute("md", md)
        }
        return "admin/majestic/pages/md/edit-md"
    }

    @GetMapping("manger-md")
    fun mangerMd(model: Model): String {
        return "admin/majestic/pages/md/manger-md"
    }

    @PostMapping("mds")
    @ResponseBody
    fun getMds(@RequestBody pageParams: PageParams): Rest {
        val mds = mdService.getVOPage(pageParams.offset.toLong(),pageParams.limit.toLong())
        return Rest.ok(mds)
    }

    @GetMapping("manger-pages")
    fun mangerPages(): String {
        return "admin/majestic/pages/pages/manger-pages"
    }

    @GetMapping("manger-posts")
    fun mangerPosts(): String {
        return "admin/majestic/pages/posts/manger-posts"
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
    fun putMd(mdId: String?, @RequestParam title: String, @RequestParam mdContent: String): Rest {
        var re = false
        if (mdId.isNullOrBlank()) {
            // insert
            re = mdService.newMd(title, mdContent)
        } else {
            // update
            re = mdService.updateMd(mdId, title, mdContent)
        }

        return if (re) Rest.ok() else Rest.failed(SystemError.ServerError("保存失败"))
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
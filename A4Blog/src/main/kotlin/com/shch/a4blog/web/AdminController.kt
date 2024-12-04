package com.shch.a4blog.web

import com.shch.a4blog.model.bo.PageParams
import com.shch.a4blog.service.IAccountService
import com.shch.a4blog.service.IMdService
import com.shch.a4blog.service.IPostService
import com.shch.a4blog.service.impl.PageService
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
class AdminController(
    val mdService: IMdService,
    val postService: IPostService,
    private val pageService: PageService
) {

    @GetMapping("", "/")
    fun adminIndex(principal: Principal): String {
        val username = principal.name
        return "admin/majestic/index"
    }

    @GetMapping("/edit-md")
    fun editMd(mdId: String?, model: Model): String {
        if (!mdId.isNullOrBlank()) {
            val md = mdService.findVOByMdId(mdId)
            model.addAttribute("md", md)
        }
        return "admin/majestic/pages/md/edit-md"
    }

    @GetMapping("/manger-md")
    fun mangerMd(model: Model): String {
        return "admin/majestic/pages/md/manger-md"
    }


    @GetMapping("/manger-pages")
    fun mangerPages(): String {
        return "admin/majestic/pages/pages/manger-pages"
    }


    @GetMapping("/manger-posts")
    fun mangerPosts(): String {
        return "admin/majestic/pages/posts/manger-posts"
    }

    /**
     * 文章预览页面
     */
    @GetMapping("/preview-post/{postId}")
    fun previewPost(@PathVariable postId: String, model: Model): String {
        val postVO = postService.getPostVOById(postId) ?: return "/themes/A4/404"
//        setMenus(model, httpRequest)
        model.addAttribute("post", postVO)
        return "themes/A4/post"
    }


    @GetMapping("/login")
    fun login(): String {
        return "admin/majestic/pages/samples/login"
    }


    @GetMapping("/te")
    fun test(model: Model): String {
        model.addAttribute("text", "COnONCoNNNNN")
        return "admin/majestic/text"
    }

    @GetMapping("/sa")
    fun ta(): Rest {
        return Rest.ok()
    }
}
package com.shch.a4blog.web

import com.shch.a4blog.model.bo.PageParams
import com.shch.a4blog.service.IAccountService
import com.shch.a4blog.service.IMdService
import com.shch.a4blog.service.IPageService
import com.shch.a4blog.service.IPostService
import com.shch.a4blog.service.impl.PageService
import com.shch.starterwebext.annotation.RestMappingController
import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.Rest.R.failed
import com.shch.starterwebext.model.vm.Rest.R.ok
import com.shch.starterwebext.model.vm.error.SystemError
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@RestMappingController("/admin")
class AdminRestController(
    val accountService: IAccountService,
    val mdService: IMdService,
    val pageService: IPageService,
    val postService: IPostService
) {


    @PostMapping("/login")
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


    @PostMapping("/put-page")
    fun createPage(pageName: String, mdId: String): Rest {
        val re = pageService.createPage(pageName, mdId)
        return if (re) Rest.ok() else Rest.failed(SystemError.ServerError("保存失败"))
    }


    @PostMapping("/mds")
    fun getMds(@RequestBody pageParams: PageParams): Rest {
        val mds = mdService.getVOPage(pageParams.offset.toLong(), pageParams.limit.toLong())
        return Rest.ok(mds)
    }

    @PostMapping("/pages")
    fun getPages(@RequestBody pageParams: PageParams): Rest {
        val pages = pageService.getVOPage(pageParams.offset, pageParams.limit)
        return Rest.ok(pages)
    }

    /**
     * 文章分页查询
     */
    @PostMapping("/posts")
    fun getPosts(@RequestBody pageParams: PageParams): Rest {
        val postsPages = postService.getVOPage(pageParams.offset, pageParams.limit)
        return Rest.ok(postsPages)
    }


}
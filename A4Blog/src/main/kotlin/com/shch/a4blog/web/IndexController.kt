package com.shch.a4blog.web

import com.shch.a4blog.model.vm.ArchiveVO
import com.shch.a4blog.model.vm.ListPageModel
import com.shch.a4blog.model.vo.PostVO
import com.shch.a4blog.service.IMenuService
import com.shch.a4blog.service.IPageService
import com.shch.a4blog.service.IPostService
import com.shch.a4blog.service.impl.PageService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping
class IndexController(val pageService: IPageService,
                      val menuService: IMenuService,
                      val postService: IPostService) {
    @GetMapping("/")
    fun index(model: Model): String {
        val menus = menuService.getMenusByU()
        val content = pageService.getPageVOByPageName("index") ?: return "/error/404"
        model.addAttribute("title", "A4")
        model.addAttribute("menus", menus)
        model.addAttribute(content)
        return "/themes/A4/index"
    }

    @GetMapping("/list")
    fun list(model: Model, httpRequest: HttpServletRequest): String {
        val listPageModel = postService.getListPageMV()

        setMenus(model, httpRequest)

        val listPageModel = ListPageModel(
            tops = listOf(PostVO("A4颜色搭配推荐", "hexo", "06/22")),
            archives = listOf(
                ArchiveVO(
                    name = "2024[11] ",
                    posts = listOf(
                        PostVO("2024年7月中后写过的无聊的诗", "情绪", "07/26"),
                        PostVO("2024年6月至7月初写过的无聊的诗", "情绪", "07/02")
                    )
                ),
                ArchiveVO(
                    name = "2023[2] ",
                    posts = listOf(
                        PostVO("2023的结束和2024的开始", "情绪", "12/31"),
                        PostVO("《山音》-（日）川端康成", "声光", "12/09")
                    )
                )
            )
        )

        model.addAttribute("page", listPageModel)
        return "/themes/A4/list"
    }

    @GetMapping("/archive/{id}")
    fun getPost(@PathVariable id:String):String{
        return "/themes/A4/post"
    }


    @GetMapping("/{path}")
    fun toPage(model: Model, @PathVariable path: String, httpRequest: HttpServletRequest): String {
        val pageVo = pageService.getPageVOByPageName(path) ?: return "/themes/A4/404"
        setMenus(model, httpRequest)
        model.addAttribute("page", pageVo)
        return "/themes/A4/page"
    }


    fun setMenus(model: Model, httpRequest: HttpServletRequest) {
        val menus = menuService.getMenusByU()
        menus.forEach {
            if (httpRequest.requestURI.equals(it.path)) {
                it.active = true
            }
        }
        model.addAttribute("menus", menus)
    }
}
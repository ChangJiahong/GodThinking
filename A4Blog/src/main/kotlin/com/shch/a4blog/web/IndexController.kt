package com.shch.a4blog.web

import com.shch.a4blog.model.vm.ListPageModel
import com.shch.a4blog.service.IMenuService
import com.shch.a4blog.service.IPageService
import com.shch.a4blog.service.IPostService
import jakarta.servlet.http.HttpServletRequest
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
        val timeLinePostVO = postService.getListPostMV()
        val topTimeLinePostVO = postService.getTopListPostVO()

        setMenus(model, httpRequest)

        val listPageModel = ListPageModel(
            tops = topTimeLinePostVO,
            archives = timeLinePostVO
        )

        model.addAttribute("page", listPageModel)
        return "/themes/A4/list"
    }

    @GetMapping("/post/{id}")
    fun getPost(@PathVariable id:String,model: Model,httpRequest: HttpServletRequest):String{
        val postVO = postService.getPostVOById(id) ?: "/themes/A4/404"
        setMenus(model, httpRequest)
        model.addAttribute("post", postVO)
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
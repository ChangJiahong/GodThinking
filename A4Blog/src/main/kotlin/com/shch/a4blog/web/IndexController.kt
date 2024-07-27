package com.shch.a4blog.web

import com.shch.a4blog.service.IPageService
import com.shch.a4blog.service.impl.PageService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping
class IndexController(val pageService: IPageService) {
    @GetMapping("/")
    fun index(model: Model): String {

        val content = pageService.getPageVOByPageName("index")

        model.addAttribute("title", "A4")
        model.addAttribute(content)
        return "/themes/A4/index"
    }

    @GetMapping("/list","/list/")
    fun list(model: Model): String {
        return "/themes/A4/list"
    }
}
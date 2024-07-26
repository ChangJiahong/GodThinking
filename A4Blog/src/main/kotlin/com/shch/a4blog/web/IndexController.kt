package com.shch.a4blog.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping
class IndexController() {
    @GetMapping("/")
    fun index(model: Model): String {


        model.addAttribute("title", "A4")
        return "/themes/A4/index"
    }

    @GetMapping("/list")
    fun list(model: Model): String {
        return "/themes/A4/list"
    }
}
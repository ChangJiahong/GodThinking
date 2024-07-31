package com.shch.a4blog.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping
class AdminController {

    @GetMapping("/admin")
    fun adminIndex():String{
        return "admin/majestic/index1"
    }

    @GetMapping("/login")
    fun login():String{
        return "admin/majestic/login"
    }
}
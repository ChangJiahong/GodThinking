package com.shch.a4blog.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/admin")
class AdminController {

    @GetMapping("/")
    fun adminIndex():String{
        return "admin/majestic/index"
    }

    @GetMapping("/login")
    fun login():String{
        return "admin/majestic/login"
    }

    @PostMapping("/login")
    fun doLogin(map:Map<String,Any>):String{
        map.forEach { t, u ->

        }
        return adminIndex()
    }

    @GetMapping("te")
    fun test(model: Model):String{
        model.addAttribute("text","COnONCoNNNNN")
        return "admin/majestic/content"
    }
}
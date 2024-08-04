package com.shch.a4blog.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class AdminPagesController {
    @GetMapping("/error")
    fun error():String = "admin/majestic/pages/samples/error-404"

    @GetMapping("/error-404")
    fun error_404():String = "admin/majestic/pages/samples/error-404"

    @GetMapping("/error-500")
    fun error_500():String = "admin/majestic/pages/samples/error-500"

    @GetMapping("/buttons")
    fun buttons():String = "admin/majestic/pages/ui-features/buttons"

    @GetMapping("/dropdowns")
    fun dropdowns():String = "admin/majestic/pages/ui-features/dropdowns"

    @GetMapping("/typography")
    fun typography():String = "admin/majestic/pages/ui-features/typography"

    @GetMapping("/basic-table")
    fun basicTable():String = "admin/majestic/pages/tables/basic-table"

    @GetMapping("/blank-page")
    fun blankPage():String = "admin/majestic/pages/samples/blank-page"
}
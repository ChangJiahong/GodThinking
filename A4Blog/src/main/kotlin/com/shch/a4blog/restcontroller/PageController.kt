package com.shch.a4blog.restcontroller

import com.shch.a4blog.service.impl.PageService
import com.shch.starterwebext.annotation.RestMappingController
import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.Rest.R.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@RestMappingController("/api/public/page")
class PageController(val pageService: PageService) {
    @GetMapping("/{pageName}")
    fun getPage(@PathVariable("pageName") pageName: String): Rest {
        val pageVO = pageService.getPageVOByPageName(pageName)
        return Rest.ok(pageVO)
    }
}
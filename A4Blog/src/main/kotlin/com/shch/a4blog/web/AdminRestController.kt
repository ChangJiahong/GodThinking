package com.shch.a4blog.web

import com.shch.a4blog.service.IPageService
import com.shch.a4blog.service.impl.PageService
import com.shch.starterwebext.annotation.RestMappingController
import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.Rest.R.failed
import com.shch.starterwebext.model.vm.Rest.R.ok
import com.shch.starterwebext.model.vm.error.SystemError
import org.springframework.web.bind.annotation.PostMapping

@RestMappingController("/admin")
class AdminRestController(val pageService: IPageService) {

    @PostMapping("/put-page")
    fun createPage(pageName: String,mdId:String): Rest {
        val re = pageService.createPage(pageName, mdId)
        return if (re) Rest.ok() else Rest.failed(SystemError.ServerError("保存失败"))
    }

}
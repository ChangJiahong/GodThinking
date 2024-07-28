package com.shch.a4blog.web

import com.shch.a4blog.model.vm.ArchiveVO
import com.shch.a4blog.model.vm.ListPageModel
import com.shch.a4blog.model.vm.PostVO
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

    @GetMapping("/list", "/list/")
    fun list(model: Model): String {

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
}
package com.shch.a4blog.service.impl


import com.shch.a4blog.mapper.GtPageMapper
import com.shch.a4blog.mapper.op
import com.shch.a4blog.model.domain.GtPage
import com.shch.a4blog.model.vo.PageVO
import com.shch.a4blog.service.IPageService
import com.shch.starterwebext.model.mapper.go
import org.springframework.stereotype.Service

@Service
class PageService(val pageMapper: GtPageMapper): IPageService {
    override fun getPageVOByPageName(pageName: String): PageVO {
       val page: GtPage = pageMapper.op{selectPageByPageName(pageName)}.orElseThrow()
        return page.go()
    }
}
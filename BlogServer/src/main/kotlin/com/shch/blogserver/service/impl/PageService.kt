package com.shch.blogserver.service.impl


import com.shch.blogserver.mapper.GtPageMapper
import com.shch.blogserver.mapper.op
import com.shch.blogserver.model.domain.GtPage
import com.shch.blogserver.model.vo.PageVO
import com.shch.blogserver.service.IPageService
import com.shch.starterwebext.model.mapper.go
import org.springframework.stereotype.Service

@Service
class PageService(val pageMapper: GtPageMapper): IPageService {
    override fun getPageVOByPageName(pageName: String): PageVO {
       val page: GtPage = pageMapper.op{selectPageByPageName(pageName)}.orElseThrow()
        return page.go()
    }
}
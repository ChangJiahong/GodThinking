package com.shch.a4blog.service.impl


import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.shch.a4blog.mapper.GtPageMapper
import com.shch.a4blog.mapper.op
import com.shch.a4blog.mapper.query
import com.shch.a4blog.model.domain.GtMd
import com.shch.a4blog.model.domain.GtPage
import com.shch.a4blog.model.vo.MdVO
import com.shch.a4blog.model.vo.PageVO
import com.shch.a4blog.service.IPageService
import com.shch.starterwebext.model.mapper.go
import org.springframework.stereotype.Service
import java.util.*

@Service
class PageService(val pageMapper: GtPageMapper) : IPageService {
    override fun getPageVOByPageName(pageName: String): PageVO? {
        val page: GtPage? = pageMapper.selectPageByPageName(pageName)
        return page?.go()
    }

    override fun getVOPage(offset: Long, limit: Long): IPage<PageVO> {
        val current = offset / limit
        val page: Page<GtPage> = Page(current + 1, limit)
        val chainWrapper = KtQueryChainWrapper(pageMapper, GtPage::class.java)
        val pages: IPage<GtPage> = pageMapper.selectPage(page, chainWrapper.wrapper)
        val voPage: IPage<PageVO> = pages.convert {
            it.go()
        }
        return voPage
    }

    override fun createPage(pageName: String, mdId: String): Boolean {
        val r=pageMapper.insert(GtPage().apply {
            this.pageName = pageName
            this.mdId = mdId
            this.createTime = Date()
        })
        return r>0
    }
}
package com.shch.a4blog.service

import com.baomidou.mybatisplus.core.metadata.IPage
import com.shch.a4blog.model.vo.PageVO


interface IPageService {
    fun getPageVOByPageName(pageName: String): PageVO?

    fun getVOPage(offset: Long, limit: Long): IPage<PageVO>
    fun createPage(pageName: String, mdId: String): Boolean
}
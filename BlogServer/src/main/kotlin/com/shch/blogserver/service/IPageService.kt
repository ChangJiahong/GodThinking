package com.shch.blogserver.service

import com.shch.blogserver.model.vo.PageVO


interface IPageService {
    fun getPageVOByPageName(pageName: String): PageVO
}
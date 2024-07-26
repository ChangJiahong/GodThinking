package com.shch.a4blog.service

import com.shch.a4blog.model.vo.PageVO


interface IPageService {
    fun getPageVOByPageName(pageName: String): PageVO
}
package com.shch.a4blog.service

import com.shch.a4blog.model.vm.ListPageModel

interface IPostService {
    fun getListPageMV(): ListPageModel
}
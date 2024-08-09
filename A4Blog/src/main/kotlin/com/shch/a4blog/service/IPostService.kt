package com.shch.a4blog.service

import com.shch.a4blog.model.vm.ListPageModel
import com.shch.a4blog.model.vm.TimeLinePostVO
import com.shch.a4blog.model.vo.PostVO

interface IPostService {
    fun getListPostMV(isTop:Boolean=false): List<TimeLinePostVO>

    fun getTopListPostVO(): List<PostVO>
}
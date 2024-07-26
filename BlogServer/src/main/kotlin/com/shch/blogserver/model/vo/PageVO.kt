package com.shch.blogserver.model.vo

import com.shch.blogserver.model.struct.PageStruct
import com.shch.starterwebext.annotation.AutoStruct

@AutoStruct(PageStruct::class)
data class PageVO(
    var pageName: String="",

    var mdId: String="",

    var content: String="",

    var createTime: String = "")
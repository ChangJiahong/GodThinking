package com.shch.a4blog.model.vo

import com.shch.a4blog.model.struct.MdStruct
import com.shch.a4blog.model.struct.PageStruct
import com.shch.starterwebext.annotation.AutoStruct

@AutoStruct(MdStruct::class)
class MdVO(

    var mdId: String = "",

    var content: String = "",

    var updateTime: String = "",

    var createTime: String = "",

    var title: String = "",
) {
}
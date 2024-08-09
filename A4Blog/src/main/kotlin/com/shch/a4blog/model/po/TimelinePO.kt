package com.shch.a4blog.model.po

import com.shch.a4blog.model.domain.GtPost
import com.shch.a4blog.model.struct.PostStruct
import com.shch.starterwebext.annotation.AutoStruct

@AutoStruct(PostStruct::class)
data class TimelinePO(
    var name: String? = null,
    var posts: ArrayList<GtPost>? = null
)
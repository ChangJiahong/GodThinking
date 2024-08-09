package com.shch.a4blog.model.domain

import com.shch.a4blog.model.struct.PostStruct
import com.shch.starterwebext.annotation.AutoStruct

@AutoStruct(PostStruct::class)
class GTEntry {
    var name: String? = null
    var value: String? = null
}
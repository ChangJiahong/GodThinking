package com.shch.a4blog.model.vo

import com.shch.a4blog.model.struct.PostStruct
import com.shch.starterwebext.annotation.AutoStruct

@AutoStruct(PostStruct::class)
data class EntryVO(
    var name: String = "",
    var value: String = ""
) {

}
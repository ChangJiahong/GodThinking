package com.shch.a4blog.model.vo

import com.shch.a4blog.model.struct.MdStruct
import com.shch.a4blog.model.struct.MenuStruct
import com.shch.starterwebext.annotation.AutoStruct


@AutoStruct(MenuStruct::class)
data class MenuVO(
    val name:String,
    val path:String,
    var active:Boolean=false,
) {
}
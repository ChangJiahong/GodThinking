package com.shch.a4blog.model.domain

import com.baomidou.mybatisplus.annotation.TableName
import com.shch.a4blog.model.struct.MenuStruct
import com.shch.starterwebext.annotation.AutoStruct
import java.io.Serializable
import java.util.*

/**
 * @TableName GT_MENU
 */
@TableName(value = "GT_MENU")
@AutoStruct(MenuStruct::class)
class GtMenu : Serializable {
    var id: Long? = null

    var uid: String? = null

    var menuName: String? = null

    var pagePath: String? = null

    var createTime: Date? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
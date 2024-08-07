package com.shch.a4blog.model.domain

import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable
import java.util.*

/**
 * @TableName GT_MENU
 */
@TableName(value = "GT_MENU")
class GtMenu : Serializable {
    var id: Long? = null

    var uid: String? = null

    var menuName: String? = null

    var pageId: String? = null

    var createTime: Date? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
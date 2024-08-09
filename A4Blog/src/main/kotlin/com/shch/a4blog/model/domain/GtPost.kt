package com.shch.a4blog.model.domain

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.shch.a4blog.model.struct.PageStruct
import com.shch.starterwebext.annotation.AutoStruct
import java.io.Serializable
import java.util.*

/**
 * @TableName GT_POST
 */
@TableName(value = "GT_POST")
@AutoStruct(PageStruct::class)
class GtPost : Serializable {
    var id: Long? = null

    var postId: String? = null

    var title: String? = null

    var mdId: String? = null

    var enable: Int? = null

    var updateTime: Date? = null

    var createTime: Date? = null

    @TableField(exist = false)
    var gtMd: GtMd? = null
    companion object {
        private const val serialVersionUID = 1L
    }
}
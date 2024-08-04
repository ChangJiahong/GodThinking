package com.shch.a4blog.model.domain

import com.baomidou.mybatisplus.annotation.TableName
import com.shch.a4blog.model.struct.MdStruct
import com.shch.a4blog.model.struct.PageStruct
import com.shch.starterwebext.annotation.AutoStruct
import java.io.Serializable
import java.util.*

/**
 * @TableName GT_MD
 */
@TableName(value = "GT_MD")
@AutoStruct(MdStruct::class)
class GtMd : Serializable {
    var id: Long? = null

    var mdId: String? = null

    var content: String? = null

    var updateTime: Date? = null

    var createTime: Date? = null

    var title: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
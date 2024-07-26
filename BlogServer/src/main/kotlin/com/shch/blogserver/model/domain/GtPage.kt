package com.shch.blogserver.model.domain

import com.baomidou.mybatisplus.annotation.TableName
import com.shch.blogserver.model.struct.PageStruct
import com.shch.starterwebext.annotation.AutoStruct
import java.io.Serializable
import java.util.*

/**
 * @TableName GT_PAGE
 */
@TableName(value = "GT_PAGE")
@AutoStruct(PageStruct::class)
class GtPage : Serializable {
    var id: Int? = null

    var pageName: String? = null

    var mdId: String? = null

    var createTime: Date? = null

    var gtMd: GtMd? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
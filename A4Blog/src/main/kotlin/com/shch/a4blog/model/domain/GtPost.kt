package com.shch.a4blog.model.domain

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.shch.a4blog.model.struct.MdStruct
import com.shch.starterwebext.annotation.AutoStruct
import java.util.*

@TableName(value = "GT_POST")
@AutoStruct(MdStruct::class)
class GtPost{

    @TableId(type = IdType.AUTO)
    var id: Long? = null

    var postId: String? = null

    var title:String?=null

    var mdId: String? = null

    var enable:Boolean?=null

    @TableField(exist = false)
    var gtMd: GtMd? = null

    var updateTime: Date? = null

    var createTime: Date? = null

}
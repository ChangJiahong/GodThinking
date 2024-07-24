package com.shch.authserver.model.domain

import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable

/**
 * @TableName gt_roles
 */
@TableName(value = "gt_roles")
class GtRole(
    var id: Long = Long.MIN_VALUE,
    var rid:String="",
    var name: String = "",
    var attr: String = "",
    var createTime: String = "",
    var updateTime: String = ""
) : Serializable {


    companion object {
        private const val serialVersionUID = 1L
    }
}
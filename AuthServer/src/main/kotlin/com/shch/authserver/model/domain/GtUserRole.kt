package com.shch.authserver.model.domain

import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable

/**
 * @TableName GT_USER_ROLE
 */
@TableName(value = "GT_USER_ROLE")
class GtUserRole(
    var id:Long=Long.MIN_VALUE,
    var userId:String = "",
    var roleId:String = "",
    var createTime: String = "",
    var updateTime: String = ""
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}
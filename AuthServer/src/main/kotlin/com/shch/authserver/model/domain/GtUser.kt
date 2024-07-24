package com.shch.authserver.model.domain

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.shch.authserver.model.po.RolePO
import com.shch.authserver.model.struct.UserStruct
import com.shch.starterwebext.annotation.AutoStruct
import java.io.Serializable

/**
 * @TableName gt_users
 */
@TableName("GT_USERS")
@AutoStruct(UserStruct::class)
data class GtUser(
    var id: Long = Long.MIN_VALUE,
    var uid: String = "",
    var nickname: String = "",
    var email: String = "",
    var pwd: String = "",
    var createTime: String = "",
    @TableField(exist = false)
    var roles: List<String> = ArrayList()
) : Serializable {

}
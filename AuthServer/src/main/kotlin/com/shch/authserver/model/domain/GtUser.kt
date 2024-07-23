package com.shch.authserver.model.domain

import com.baomidou.mybatisplus.annotation.TableName
import com.shch.authserver.model.po.RolePO
import java.io.Serializable

/**
 * @TableName gt_users
 */
@TableName("GT_USERS")
data class GtUser(
    var id: Long = Long.MIN_VALUE,
    var uid: String = "",
    var nickname: String = "",
    var email: String = "",
    var pwd: String = "",
    var createTime: String = "",
    var roles: List<RolePO> = ArrayList()
) : Serializable {

}
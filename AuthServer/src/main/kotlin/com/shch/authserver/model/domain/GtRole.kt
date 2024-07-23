package com.shch.authserver.model.domain

import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable

/**
 * @TableName gt_roles
 */
@TableName(value = "gt_roles")
class GtRole(
    var id: Long? = null,

    var attr: String? = null,

    var createTime: String? = null,

    var name: String? = null,

    var rid: String? = null,

    var updateTime: String? = null
) : Serializable {


    companion object {
        private const val serialVersionUID = 1L
    }
}
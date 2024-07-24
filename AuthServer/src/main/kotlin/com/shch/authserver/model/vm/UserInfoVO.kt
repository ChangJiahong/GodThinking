package com.shch.authserver.model.vm

import com.shch.authserver.model.struct.UserStruct
import com.shch.starterwebext.annotation.AutoStruct

@AutoStruct(UserStruct::class)
data class UserInfoVO(
    var uid: String = "",
    var nickname: String = "",
    var email: String = "",
    var createTime: String = "",
) {
}
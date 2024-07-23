package com.shch.authserver.model.vm

import com.shch.authserver.model.struct.UserStruct
import com.shch.starterwebext.annotation.AutoStruct

@AutoStruct(UserStruct::class)
data class UserInfoVM(
    var id:Int=-1,
    var nickname: String = "") {
}
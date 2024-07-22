package com.shch.authserver.model.vm

import com.shch.authserver.model.mapper.UserMapper
import com.shch.starterwebext.model.mapper.AutoMapper

@AutoMapper(UserMapper::class)
data class UserInfoVM(
    var id:Int=-1,
    var nickname: String = "") {
}
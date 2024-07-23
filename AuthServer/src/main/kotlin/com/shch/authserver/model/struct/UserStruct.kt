package com.shch.authserver.model.struct

import com.shch.authserver.model.po.UserPO
import com.shch.authserver.model.vm.UserInfoVM
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserStruct{

    fun top(userPO: UserPO):UserInfoVM

    fun top(b: UserInfoVM): UserPO
}
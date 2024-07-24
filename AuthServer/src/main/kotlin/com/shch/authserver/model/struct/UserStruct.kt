package com.shch.authserver.model.struct

import com.shch.authserver.model.bo.UserDetailsBO
import com.shch.authserver.model.domain.GtUser
import com.shch.authserver.model.po.UserPO
import com.shch.authserver.model.vm.UserInfoVO
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface UserStruct {

    fun top(userPO: UserPO): UserInfoVO

    fun top(b: UserInfoVO): UserPO

    fun toVo(gtUser: GtUser): UserInfoVO
}
package com.shch.a4blog.model.struct

import com.shch.a4blog.model.domain.GtMd
import com.shch.a4blog.model.domain.GtMenu
import com.shch.a4blog.model.vo.MdVO
import com.shch.a4blog.model.vo.MenuVO
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.NullValueCheckStrategy
import java.awt.Menu


@Mapper(componentModel = "spring")
interface MenuStruct {
    @Mappings(
        Mapping(
            target = "path",
            source = "pagePath",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        ),
        Mapping(
            target = "name",
            source = "menuName",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        )
    )
    fun toModel(gtMenu: GtMenu): MenuVO

    fun toList(list:List<GtMenu>):List<MenuVO>

}
package com.shch.a4blog.model.struct

import com.shch.a4blog.model.domain.GtMd
import com.shch.a4blog.model.domain.GtPage
import com.shch.a4blog.model.vo.MdVO
import com.shch.a4blog.model.vo.PageVO
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface MdStruct {

    @Mappings(
        Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy-MM-dd"),
        Mapping(source = "updateTime", target = "updateTime", dateFormat = "yyyy-MM-dd"),
    )
    fun toModel(gtMd: GtMd): MdVO
}
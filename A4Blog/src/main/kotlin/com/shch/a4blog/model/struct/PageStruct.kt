package com.shch.a4blog.model.struct

import com.shch.a4blog.model.domain.GtPage
import com.shch.a4blog.model.vo.PageVO
import org.mapstruct.*

@Mapper(componentModel = "spring")
interface PageStruct {

    @Mappings(
        Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy-MM-dd"),
        Mapping(
            target = "content",
            source = "gtMd.content",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        )
    )
    fun toModel(gtPage: GtPage): PageVO
}
package com.shch.a4blog.model.struct

import com.shch.a4blog.model.domain.GtPost
import com.shch.a4blog.model.vo.PostVO
import org.mapstruct.*

@Mapper(componentModel = "spring")
interface PostStruct {

    @Mappings(
        Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy-MM-dd"),
        Mapping(source = "updateTime", target = "updateTime", dateFormat = "yyyy-MM-dd"),
        Mapping(
            target = "content",
            source = "gtMd.content",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        )
    )
    fun toModel(gtPage: GtPost): PostVO
}
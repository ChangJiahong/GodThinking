package com.shch.a4blog.model.struct

import com.shch.a4blog.model.domain.GTEntry
import com.shch.a4blog.model.domain.GtPost
import com.shch.a4blog.model.po.TimelinePO
import com.shch.a4blog.model.vm.TimeLinePostVO
import com.shch.a4blog.model.vo.EntryVO
import com.shch.a4blog.model.vo.PostVO
import org.mapstruct.*

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
interface PostStruct {

    @Mappings(
        Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy-MM-dd"),
        Mapping(source = "updateTime", target = "updateTime", dateFormat = "yyyy-MM-dd"),
        Mapping(
            target = "content",
            source = "gtMd.content"
        )
    )
    fun toModel(gtPage: GtPost): PostVO

    fun toModel3(model: GTEntry): EntryVO

    fun toList(gtPages: List<GtPost>): List<PostVO>

    fun toModel1(timelinePO: TimelinePO): TimeLinePostVO

    fun toList1(timelinePOs: List<TimelinePO>): List<TimeLinePostVO>
}
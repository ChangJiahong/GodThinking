package com.shch.a4blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.shch.a4blog.model.domain.GtPost
import com.shch.a4blog.model.po.TimelinePO
import org.apache.ibatis.annotations.Mapper

/**
 * @author changjiahong
 * @description 针对表【GT_POST】的数据库操作Mapper
 * @createDate 2024-08-08 21:46:11
 * @Entity generator.domain.GtPost
 */
@Mapper
interface GtPostMapper : BaseMapper<GtPost> {

    fun selectTimelinePostsList(isTop:Boolean): List<TimelinePO>

    fun selectPostsList(isTop:Boolean): List<GtPost>

    fun selectPostById(postId: String): GtPost?
}





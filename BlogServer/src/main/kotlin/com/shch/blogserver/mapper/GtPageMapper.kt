package com.shch.blogserver.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.shch.blogserver.model.domain.GtPage
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

/**
 * @author juxiaoxue
 * @description 针对表【GT_PAGE】的数据库操作Mapper
 * @createDate 2024-07-25 14:58:48
 * @Entity com.shch.a4blog.domain.GtPage
 */
@Mapper
interface GtPageMapper : IBaseMapper<GtPage, GtPageMapper> {

//    @Select("select * from Gt_Page where page_name = #{pageName}")
    fun selectPageByPageName(pageName: String): GtPage?
}





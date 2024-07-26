package com.shch.a4blog.mapper

import com.shch.a4blog.model.domain.GtPage
import org.apache.ibatis.annotations.Mapper

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





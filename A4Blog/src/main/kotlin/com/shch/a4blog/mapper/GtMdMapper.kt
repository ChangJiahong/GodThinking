package com.shch.a4blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.shch.a4blog.model.domain.GtMd
import org.apache.ibatis.annotations.Mapper

/**
 * @author juxiaoxue
 * @description 针对表【GT_MD】的数据库操作Mapper
 * @createDate 2024-07-25 14:58:26
 * @Entity com.shch.a4blog.domain.GtMd
 */
@Mapper
interface GtMdMapper : BaseMapper<GtMd>{

    fun selectByMdId(mdId:String): GtMd?
}





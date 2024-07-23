package com.shch.authserver.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.shch.authserver.model.domain.GtUser
import org.apache.ibatis.annotations.Mapper

/**
 * @author juxiaoxue
 * @description 针对表【gt_users】的数据库操作Mapper
 * @createDate 2024-07-23 19:19:27
 * @Entity com.shch.authserver.domain.GtUser
 */
@Mapper
interface GtUserMapper : BaseMapper<GtUser>





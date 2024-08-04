package com.shch.a4blog.service.impl

import com.shch.a4blog.mapper.GtMdMapper
import com.shch.a4blog.mapper.op
import com.shch.a4blog.mapper.query
import com.shch.a4blog.model.domain.GtMd
import com.shch.a4blog.model.domain.GtPage
import com.shch.a4blog.model.vo.MdVO
import com.shch.a4blog.service.IMdService
import com.shch.starterwebext.model.mapper.go
import org.springframework.stereotype.Service

@Service
class MdService(val mdMapper: GtMdMapper) : IMdService {
    override fun findVOByMdId(mdId: String): MdVO? {
        val page: GtMd? = mdMapper.query().eq(GtMd::mdId, mdId).one()

        return page?.go()
    }
}
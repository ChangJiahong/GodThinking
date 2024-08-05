package com.shch.a4blog.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper
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

    override fun findVO(): List<MdVO> {
        val mds = mdMapper.query().list()
        return mds.go()
    }

    override fun newMd(title: String, mdContent: String): Boolean {

        val re = mdMapper.insert(GtMd().apply {
            this.title = title
            this.content = mdContent
        })
        return re > 0
    }

    override fun updateMd(mdId: String, title: String, mdContent: String): Boolean {

        val re = KtUpdateChainWrapper(mdMapper, GtMd::class.java).eq(GtMd::mdId, mdId).update(GtMd().apply {
            this.mdId = mdId
            this.title = title
            this.content = mdContent
        })
        return re
    }
}
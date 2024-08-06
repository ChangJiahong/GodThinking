package com.shch.a4blog.service.impl

import cn.hutool.core.util.IdUtil
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.shch.a4blog.mapper.GtMdMapper
import com.shch.a4blog.mapper.query
import com.shch.a4blog.model.domain.GtMd
import com.shch.a4blog.model.vo.APage
import com.shch.a4blog.model.vo.MdVO
import com.shch.a4blog.service.IMdService
import com.shch.starterwebext.model.mapper.go
import org.springframework.stereotype.Service
import java.util.*

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


    override fun getVOPage(offset: Long, limit: Long): IPage<MdVO> {
        val current = offset / limit
        val page: Page<GtMd> = Page(current+1, limit)
        val chainWrapper = KtQueryChainWrapper(mdMapper,GtMd::class.java)
        val pages: IPage<GtMd> = mdMapper.selectPage(page, chainWrapper.wrapper)
        val voPage: IPage<MdVO> = pages.convert {
            it.go()
        }

        return voPage
    }

    override fun newMd(title: String, mdContent: String): Boolean {

        val re = mdMapper.insert(GtMd().apply {
            mdId = IdUtil.getSnowflake().nextIdStr()
            this.title = title
            this.content = mdContent
            this.updateTime = Date()
            this.createTime = updateTime
        })
        return re > 0
    }

    override fun updateMd(mdId: String, title: String, mdContent: String): Boolean {

        val re = KtUpdateChainWrapper(mdMapper, GtMd::class.java).eq(GtMd::mdId, mdId).update(GtMd().apply {
            this.title = title
            this.content = mdContent
            this.updateTime = Date()
        })
        return re
    }
}
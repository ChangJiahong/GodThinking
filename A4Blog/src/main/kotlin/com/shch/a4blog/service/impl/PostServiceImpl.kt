package com.shch.a4blog.service.impl

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.shch.a4blog.mapper.GtPostMapper
import com.shch.a4blog.model.domain.GtPage
import com.shch.a4blog.model.domain.GtPost
import com.shch.a4blog.model.po.TimelinePO
import com.shch.a4blog.model.vm.TimeLinePostVO
import com.shch.a4blog.model.vo.PageVO
import com.shch.a4blog.model.vo.PostVO
import com.shch.a4blog.service.IPostService
import com.shch.starterwebext.model.mapper.go
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(val postMapper: GtPostMapper) : IPostService {

    override fun getListPostMV(isTop: Boolean): List<TimeLinePostVO> {
        val timelinePO: List<TimelinePO> = postMapper.selectTimelinePostsList(isTop)
        val tmvo: List<TimeLinePostVO> = timelinePO.go()
        return tmvo
    }

    override fun getTopListPostVO(): List<PostVO> {
        val gtPosts: List<GtPost> = postMapper.selectPostsList(true)
        return gtPosts.go()
    }

    override fun getPostVOById(id: String): PostVO? {
        val gtPost = postMapper.selectPostById(id)
        return gtPost?.go()
    }

    override fun getVOPage(offset: Long, limit: Long): IPage<PostVO> {
        val current = offset / limit
        val page: Page<GtPost> = Page(current + 1, limit)
        val pages: IPage<GtPost> = postMapper.selectPostsPages(page)
        val voPage: IPage<PostVO> = pages.convert {
            it.go()
        }
        return voPage
    }
}
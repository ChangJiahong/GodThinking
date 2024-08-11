package com.shch.a4blog.service.impl

import com.shch.a4blog.mapper.GtPostMapper
import com.shch.a4blog.model.domain.GtPost
import com.shch.a4blog.model.po.TimelinePO
import com.shch.a4blog.model.vm.TimeLinePostVO
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
}
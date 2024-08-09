package com.shch.a4blog.model.vm

import com.shch.a4blog.model.vo.PostVO

data class ListPageModel(val tops: List<PostVO>, val archives: List<TimeLinePostVO>) {
}

data class TimeLinePostVO(val name: String, val posts: List<PostVO>)


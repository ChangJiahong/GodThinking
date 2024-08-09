package com.shch.a4blog.model.vm

import com.shch.a4blog.model.vo.PostVO

data class ListPageModel(val tops: List<PostVO>, val archives: List<ArchiveVO>) {
}

data class ArchiveVO(val name: String, val posts: List<PostVO>)


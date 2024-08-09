package com.shch.a4blog.model.vo

import com.shch.a4blog.model.domain.GTEntry

data class PostVO(
    var postId: String="",
    var title: String="",
    var category: EntryVO? = null,
    var tags: List<GTEntry> = listOf(),
    var content: String = "",
    var updateTime: String = "",
    var createTime: String = "",
)
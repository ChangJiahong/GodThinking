package com.shch.a4blog.model.vm

data class ListPageModel(val tops: List<PostVO>, val archives: List<ArchiveVO>) {
}

data class ArchiveVO(val name: String, val posts: List<PostVO>)

data class PostVO(val title: String, val categorie: String, val publishDate: String)
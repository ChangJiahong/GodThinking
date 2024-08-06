package com.shch.a4blog.model.bo

import org.springframework.web.bind.annotation.RequestParam

data class PageParams(
    val offset: Long = 0,
    val limit: Long = 10
) {
}
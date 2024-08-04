package com.shch.a4blog.service

import com.shch.a4blog.model.vo.MdVO

interface IMdService {

    fun findVOByMdId(mdId: String): MdVO?

}
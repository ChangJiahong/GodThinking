package com.shch.a4blog.service

import com.shch.a4blog.model.vo.MdVO

interface IMdService {

    fun findVOByMdId(mdId: String): MdVO?

    fun findVO():List<MdVO>

    fun newMd(title: String, mdContent: String): Boolean

    fun updateMd(mdId: String, title: String, mdContent: String): Boolean

}
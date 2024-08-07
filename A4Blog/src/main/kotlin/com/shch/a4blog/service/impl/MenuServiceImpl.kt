package com.shch.a4blog.service.impl

import com.shch.a4blog.mapper.GtMenuMapper
import com.shch.a4blog.mapper.query
import com.shch.a4blog.model.domain.GtMenu
import com.shch.a4blog.model.vo.MenuVO
import com.shch.a4blog.service.IMenuService
import com.shch.starterwebext.model.mapper.go
import org.springframework.stereotype.Service

@Service
class MenuServiceImpl(val menuMapper: GtMenuMapper):IMenuService {
    override fun getMenusByU(): List<MenuVO> {
        val mlist:List<GtMenu> =menuMapper.query().list()
        return mlist.go<GtMenu, MenuVO>()
    }
}
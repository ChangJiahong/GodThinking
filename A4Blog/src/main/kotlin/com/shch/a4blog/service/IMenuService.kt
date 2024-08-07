package com.shch.a4blog.service

import com.shch.a4blog.model.vo.MenuVO

interface IMenuService {
    fun getMenusByU():List<MenuVO>
}
package com.shch.authserver.model.po

import jakarta.persistence.*

@Entity
@Table(name = "gt_roles")
data class RolePO(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //自增
    var id: Long = -1,
    var rid:String="",
    var name: String = "",
    var attr: String = "",
    var createTime: String = "",
    var updateTime: String = ""
) {

}
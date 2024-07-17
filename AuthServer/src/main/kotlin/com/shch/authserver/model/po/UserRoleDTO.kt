package com.shch.authserver.model.po

import jakarta.persistence.*


@Entity
@Table(name = "gt_user_role")
data class UserRoleDTO(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //自增
    var id:Long=-1,
    var userId:String = "",
    var roleId:String = "",
    var createTime: String = "",
    var updateTime: String = ""
) {

}
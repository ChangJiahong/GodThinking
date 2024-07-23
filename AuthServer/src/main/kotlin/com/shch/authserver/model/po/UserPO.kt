package com.shch.authserver.model.po

import com.shch.authserver.model.struct.UserStruct
import com.shch.starterwebext.annotation.AutoStruct
import jakarta.persistence.*

@Entity
@Table(name = "gt_users")
@AutoStruct(UserStruct::class)
data class UserPO(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //自增
    var id: Long = -1,
    var uid: String = "",
    var nickname: String = "",
    var email: String = "",
    var pwd: String = "",
    var createTime: String = "",
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "gt_user_role",
        joinColumns = [JoinColumn(name = "userId",referencedColumnName="uid")],
        inverseJoinColumns = [JoinColumn(name = "roleId",referencedColumnName="rid")]
    )
    var roles: List<RolePO> = ArrayList()
) {

}

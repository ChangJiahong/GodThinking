package com.shch.authserver.model.po

import com.shch.authserver.model.mapper.UserMapper
import com.shch.starterwebext.annotation.AutoMapper
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "gt_users")
@AutoMapper(UserMapper::class)
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

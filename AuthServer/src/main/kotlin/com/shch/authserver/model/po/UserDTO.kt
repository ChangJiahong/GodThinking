package com.shch.authserver.model.po

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "gt_users")
data class UserDTO(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //自增
    var id: Long=-1,
    var uid: String="",
    var nickname: String="",
    var email: String="",
    var pwd: String="",
    var createTime: String="",
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gt_user_role",joinColumns = [JoinColumn(name = "userId")],inverseJoinColumns = [JoinColumn(name="roleId")])
    var roles: List<RoleDTO> =ArrayList()
): UserDetails{


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        if (roles.isEmpty()) {
            return ArrayList()
        }
        val authorities: MutableList<SimpleGrantedAuthority> = ArrayList()
        for (role in roles) {
            authorities.add(SimpleGrantedAuthority(role.attr))
        }
        return authorities
    }

    override fun getPassword(): String =pwd

    override fun getUsername(): String =email

    override fun isAccountNonExpired(): Boolean =true

    override fun isAccountNonLocked(): Boolean =true

    override fun isCredentialsNonExpired(): Boolean =true

    override fun isEnabled(): Boolean =true
}

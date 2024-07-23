package com.shch.authserver.model.bo

import com.shch.authserver.model.domain.GtUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserDetailsBO(
    var uid: String = "",
    var nickname: String = "",
    var email: String = "",
    var pwd: String = "",
    var createTime: String = "",
    private var _authorities: MutableCollection<out GrantedAuthority> = ArrayList()
) : UserDetails {

    constructor(user: GtUser) : this(
        uid = user.uid,
        nickname = user.nickname,
        email = user.email,
        pwd = user.pwd,
        createTime = user.createTime
    ) {
        val authorities: MutableList<SimpleGrantedAuthority> = ArrayList()
        val roles = user.roles
        for (role in roles) {
            authorities.add(SimpleGrantedAuthority(role.attr))
        }
        this._authorities = authorities
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return _authorities
    }

    override fun getPassword(): String = pwd

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
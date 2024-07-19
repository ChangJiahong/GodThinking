package com.shch.authserver.extmod.oidc

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken


/**
 * 自定义 OidcToken
 *
 * @author Ray Hao
 * @since 3.1.0
 */
class CustomOidcToken : OidcUserInfoAuthenticationToken {
    private val principal: Authentication

    private val userInfo: CustomOidcUserInfo

//    constructor(principal: Authentication) : super(principal) {
////        Assert.notNull(principal, "principal cannot be null")
//        this.principal = principal
//        this.userInfo = null
//        this.isAuthenticated = false
//    }

    constructor(principal: Authentication, userInfo: CustomOidcUserInfo) : super(principal, userInfo) {
        //        Assert.notNull(principal, "principal cannot be null");
//        Assert.notNull(userInfo, "userInfo cannot be null");
        this.principal = principal
        this.userInfo = userInfo
        this.isAuthenticated = true
    }


    override fun getPrincipal(): Any {
        return this.principal
    }

    override fun getCredentials(): Any {
        return ""
    }

    override fun getUserInfo(): OidcUserInfo {
        return this.userInfo
    }
}
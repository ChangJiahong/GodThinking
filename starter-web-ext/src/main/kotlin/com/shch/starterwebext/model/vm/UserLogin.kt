package com.shch.starterwebext.model.vm

/**
 *
 * @des
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
data class UserLogin(
    val username: String,
    val userIcon: String,
    val age: Int,
    val token: String,
    val refreshToken: String
) {
}
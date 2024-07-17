package com.shch.authserver.service

import com.shch.authserver.model.po.DemoPo
import org.springframework.security.core.userdetails.UserDetailsService

/**
 *
 * @des
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
interface ITestService {

    fun test(): List<DemoPo>
}
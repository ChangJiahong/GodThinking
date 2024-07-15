package com.shch.authserver.repository

import com.shch.authserver.model.po.DemoPo
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @des
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
interface TestRepository: JpaRepository<DemoPo, Int> {
}
package com.shch.authserver.repository

import com.shch.authserver.model.po.DemoPo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @des
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
@Repository
interface TestRepository: JpaRepository<DemoPo, Int> {
}
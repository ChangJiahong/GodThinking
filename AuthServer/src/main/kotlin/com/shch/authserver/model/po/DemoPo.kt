package com.shch.authserver.model.po

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 *
 * @des
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
@Entity
@Table(name = "demo")
data class DemoPo(
    @Id
    var id: Int=1,
    var name: String = ""
) {
}
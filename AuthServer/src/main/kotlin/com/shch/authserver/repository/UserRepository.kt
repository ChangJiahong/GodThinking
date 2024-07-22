package com.shch.authserver.repository

import com.shch.authserver.model.po.UserPO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserPO, Long>{
}
package com.shch.authserver.repository

import com.shch.authserver.model.po.UserDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserDTO, Long>{
}
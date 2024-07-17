package com.shch.authserver.service

import com.shch.authserver.model.po.DemoPo
import com.shch.authserver.repository.TestRepository
import com.shch.authserver.service.ITestService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

/**
 *
 * @des
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
@Service
class TestService(val iTestRepository: TestRepository): ITestService {

    override fun test(): List<DemoPo> {
        val test = iTestRepository.findAll()
        return test
    }


}
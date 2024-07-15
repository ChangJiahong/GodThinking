package com.shch.authserver.config

import com.shch.authserver.model.vm.Rest
import com.shch.authserver.model.vm.Rest.R.failed
import com.shch.authserver.model.vm.error.RestCode
import com.shch.authserver.model.vm.error.SystemError
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import kotlin.RuntimeException

@ControllerAdvice
@ResponseBody
class GlobalExceptionHandling {

    @ExceptionHandler(RestCode::class)
    fun customExp(restCode: RestCode): Rest {
        return Rest.failed(restCode)
    }

    @ExceptionHandler(RuntimeException::class)
    fun exp(runtimeException: RuntimeException): Rest {
        return customExp(SystemError.ServerError(runtimeException.toString() ?: ""))
    }
}
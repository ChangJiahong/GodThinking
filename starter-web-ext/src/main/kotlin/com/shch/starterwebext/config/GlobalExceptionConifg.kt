package com.shch.starterwebext.config

import com.shch.starterwebext.model.vm.Rest
import com.shch.starterwebext.model.vm.Rest.R.failed
import com.shch.starterwebext.model.vm.error.RestCode
import com.shch.starterwebext.model.vm.error.SystemError
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import kotlin.RuntimeException

@ControllerAdvice
@ResponseBody
class GlobalExceptionConifg {

    @ExceptionHandler(RestCode::class)
    fun customExp(restCode: RestCode): Rest {
        return Rest.failed(restCode)
    }

    @ExceptionHandler(RuntimeException::class)
    fun exp(runtimeException: RuntimeException): Rest {
        return customExp(SystemError.ServerError(runtimeException.toString()))
    }
}
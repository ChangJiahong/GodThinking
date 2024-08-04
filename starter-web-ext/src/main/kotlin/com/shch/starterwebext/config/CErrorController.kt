package com.shch.starterwebext.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.servlet.ModelAndView
import java.util.*

/**
 *
 * @des
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
@RestController
class CErrorController(
    val errorAttributes: DefaultErrorAttributes = DefaultErrorAttributes(),
    serverProperties: ServerProperties
) : BasicErrorController(errorAttributes, serverProperties.error) {
    @RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun error(request: HttpServletRequest): ResponseEntity<MutableMap<String, Any>> {
        val exp = errorAttributes.getError(ServletWebRequest(request))
        val body = getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE))
        val servletRequestAttributes: ServletRequestAttributes =
            RequestContextHolder.getRequestAttributes() as ServletRequestAttributes;
        //可以通过断点调试来查看body中的信息
        val status = getStatus(request)
        val response = HashMap<String, Any>()
        val statusId = body["message"].toString()
//        val statusMsg = StatusMsg.valueOfId(statusId)
        response["code"] = status.value()
        response["msg"] = statusId
        val httpServletResponse = servletRequestAttributes.response
        httpServletResponse?.status = 200
        return ResponseEntity<MutableMap<String, Any>>(response, status)
    }

    override fun errorHtml(request: HttpServletRequest?, response: HttpServletResponse?): ModelAndView {
        val status = this.getStatus(request)
        val model = Collections.unmodifiableMap(
            this.getErrorAttributes(
                request,
                this.getErrorAttributeOptions(request, MediaType.TEXT_HTML)
            )
        )
        response!!.status = status.value()
        val errorViewName = when (status.value()) {
            404, 500 -> "error/${status.value()}"
            else -> "error/error"
        }
        return ModelAndView(errorViewName, model)
    }
}
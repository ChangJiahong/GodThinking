package com.shch.starterwebext.utils

import com.alibaba.fastjson.JSON
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException
import java.io.PrintWriter

@Throws(IOException::class)
fun HttpServletResponse.print(obj: Any?) {

    this.printJson(JSON.toJSONString(obj))
}

@Throws(IOException::class)
fun HttpServletResponse.printJson(msg: String?) {
    print(msg,"application/json;charset=UTF-8")
}

@Throws(IOException::class)
fun HttpServletResponse.print(msg: String?) {
    print(msg,"text/html;charset=UTF-8")
}

@Throws(IOException::class)
fun HttpServletResponse.print(msg: String?, contentType: String) {
    //在响应中主动告诉浏览器使用UTF-8编码格式来接收数据application/json;charset=UTF-8 text/html;charset=UTF-8
    setHeader("Content-Type", contentType)
    //可以使用封装类简写Content-Type，使用该方法则无需使用setCharacterEncoding
    setContentType(contentType)
    val pw: PrintWriter = writer
    pw.print(msg)
}
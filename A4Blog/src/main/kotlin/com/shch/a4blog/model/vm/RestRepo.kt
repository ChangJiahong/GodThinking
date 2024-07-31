package com.shch.a4blog.model.vm

import com.shch.starterwebext.model.vm.Rest

class RestRepo<T>(override val code: Int, override val msg: String? = null, override val data: T? = null) :
    Rest(code, msg, data)

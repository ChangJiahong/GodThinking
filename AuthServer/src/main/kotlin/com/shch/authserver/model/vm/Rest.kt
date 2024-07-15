package com.shch.authserver.model.vm

import com.shch.authserver.model.vm.error.OK
import com.shch.authserver.model.vm.error.RestCode
import com.shch.authserver.model.vm.error.toPair

/**
 * RestResponse
 * @des
 * @author ChangJiahong
 * @date 2024/7/11
 * Create By IDEA
 */
class Rest private constructor(val code: Int, val msg: String? = null, val data: Any? = null) {
    companion object R {

        private fun R.pair(pair: Pair<Int, String>, data: Any? = null) = Rest(pair.first, pair.second, data)

        fun R.parse(state: RestCode, data: Any? = null) = Rest.pair(state.toPair(), data)

        fun R.ok(state: RestCode, data: Any? = null) = Rest.parse(state, data)

        fun R.ok(data: Any? = null) = ok(OK, data)

        fun R.failed(state: RestCode, data: Any? = null) = Rest.parse(state, data)
    }
}

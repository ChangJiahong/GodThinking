package com.shch.authserver.extmod.oidc

import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import java.util.function.Consumer
import kotlin.collections.LinkedHashMap


/**
 * 自定义 OidcUserInfo
 *
 * @author Ray Hao
 * @since 3.1.0
 */
class CustomOidcUserInfo(claims: Map<String, Any>) : OidcUserInfo(claims) {
    private val claims: Map<String, Any> = LinkedHashMap<String,Any>(claims)

    override fun getClaims(): Map<String, Any> {
        return this.claims
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        } else if (other != null && this.javaClass == other.javaClass) {
            val that = other as CustomOidcUserInfo
            return this.getClaims() == that.getClaims()
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        return getClaims().hashCode()
    }

    class Builder internal constructor() {
        private val claims: MutableMap<String, Any> = LinkedHashMap()

        fun claim(name: String, value: Any): Builder {
            claims[name] = value
            return this
        }

        fun claims(claimsConsumer: Consumer<Map<String, Any>>): Builder {
            claimsConsumer.accept(this.claims)
            return this
        }

        fun username(username: String): Builder {
            return this.claim("username", username)
        }

        fun nickname(nickname: String): Builder {
            return this.claim("nickname", nickname)
        }

        fun description(description: String): Builder {
            return this.claim("description", description)
        }

        fun status(status: Int): Builder {
            return this.claim("status", status)
        }

        fun phoneNumber(phoneNumber: String): Builder {
            return this.claim("phone_number", phoneNumber)
        }

        fun email(email: String): Builder {
            return this.claim("email", email)
        }

        fun profile(profile: String): Builder {
            return this.claim("profile", profile)
        }

        fun address(address: String): Builder {
            return this.claim("address", address)
        }

        fun build(): CustomOidcUserInfo {
            return CustomOidcUserInfo(this.claims)
        }
    }

    companion object {
        private const val serialVersionUID = 610L
        fun customBuilder(): Builder {
            return Builder()
        }
    }
}
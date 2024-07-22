package com.shch.starterwebext.handler

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.core.convert.converter.Converter
import org.springframework.core.log.LogMessage
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.util.Assert
import org.springframework.util.StringUtils
import java.util.*

/**
 * Extracts the [GrantedAuthority]s from scope attributes typically found in a
 * [Jwt].
 *
 * @author Eric Deandrea
 * @since 5.2
 */
class CusJwtGrantedAuthoritiesConverter : Converter<Jwt, Collection<GrantedAuthority>> {
    private val logger: Log = LogFactory.getLog(javaClass)

    private var authoritiesClaimDelimiter = DEFAULT_AUTHORITIES_CLAIM_DELIMITER

    /**
     * Extract [GrantedAuthority]s from the given [Jwt].
     * @param jwt The [Jwt] token
     * @return The [authorities][GrantedAuthority] read from the token scopes
     */
    override fun convert(jwt: Jwt): Collection<GrantedAuthority> {
        val grantedAuthorities: MutableCollection<GrantedAuthority> = ArrayList()
        for (authority in getAuthorities(jwt)) {
            grantedAuthorities.add(SimpleGrantedAuthority(authority))
        }
        return grantedAuthorities
    }

    /**
     * Sets the regex to use for splitting the value of the authorities claim into
     * [authorities][GrantedAuthority]. Defaults to
     * [org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter.DEFAULT_AUTHORITIES_CLAIM_DELIMITER].
     * @param authoritiesClaimDelimiter The regex used to split the authorities
     * @since 6.1
     */
    fun setAuthoritiesClaimDelimiter(authoritiesClaimDelimiter: String) {
        Assert.notNull(authoritiesClaimDelimiter, "authoritiesClaimDelimiter cannot be null")
        this.authoritiesClaimDelimiter = authoritiesClaimDelimiter
    }

    private fun getAuthoritiesClaimName(jwt: Jwt): Collection<String> {
        val claimNames = mutableListOf<String>()
        for (claimName in WELL_KNOWN_AUTHORITIES_CLAIM_NAMES) {
            if (jwt.hasClaim(claimName)) {
                claimNames.add(claimName)
            }
        }
        return claimNames
    }

    private fun getAuthorities(jwt: Jwt): Collection<String> {
        val claimNames = getAuthoritiesClaimName(jwt)
        if (claimNames.isEmpty()) {
            logger.trace("Returning no authorities since could not find any claims that might contain scopes")
            return emptyList()
        }
        if (logger.isTraceEnabled) {
            logger.trace(LogMessage.format("Looking for scopes in claim %s", claimNames))
        }
        val authoritieMaps = mutableListOf<String>()
        claimNames.forEach { claimName ->
            val authorities = jwt.getClaim<Any>(claimName)
            if (authorities is String) {
                if (StringUtils.hasText(authorities)) {

                    authorities.split(
                        authoritiesClaimDelimiter.toRegex()
                    ).dropLastWhile { it.isEmpty() }.forEach { authority ->
                        authoritieMaps.add("${claimName.uppercase()}_${authority}")
                    }
                }
                return@forEach
            }
            if (authorities is Collection<*>) {
                castAuthoritiesToCollection(authorities).forEach { authority ->
                    authoritieMaps.add("${claimName.uppercase()}_${authority}")
                }
            }
        }

        return authoritieMaps
    }

    private fun castAuthoritiesToCollection(authorities: Any): Collection<String> {
        return authorities as Collection<String>
    }

    companion object {

        private const val DEFAULT_AUTHORITIES_CLAIM_DELIMITER = " "

        private val WELL_KNOWN_AUTHORITIES_CLAIM_NAMES: Collection<String> = mutableListOf("scope", "scp", "role")
    }
}

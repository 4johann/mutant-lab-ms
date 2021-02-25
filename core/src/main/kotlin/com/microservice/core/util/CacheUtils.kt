package com.microservice.core.util

import java.math.BigInteger
import java.security.MessageDigest

object CacheUtils {

    fun getDnaKey(dna: List<String>): String {
        val joinDna = StringBuilder()
        dna.map { joinDna.append(it) }
        return BigInteger(
            1,
            MessageDigest.getInstance("MD5").digest(joinDna.toString().toByteArray())
        ).toString(16).padStart(32, '0')
    }
}

package com.cj.smartworker.domain.util

import java.security.MessageDigest
import java.util.*

class PasswordEncodeUtil {
    companion object {
        private val messageDigest: MessageDigest = MessageDigest.getInstance("SHA-512")

        fun encode(password: String): String {
            val bytes = messageDigest.digest(password.toByteArray())
            return Base64.getEncoder().encodeToString(bytes)
        }

        fun matches(password: String, encodedPassword: String): Boolean {
            val newEncodedPassword = encode(password)
            return newEncodedPassword == encodedPassword
        }
    }
}

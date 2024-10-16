package com.cj.smartworker.util

import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class HeartRateEncodingUtil {
    companion object {

        private const val ALGORITHM = "AES/CBC/PKCS5Padding"
        private const val KEY_SIZE = 256
        private const val IV_SIZE = 16

        fun encrypt(plainText: String, secretKey: String): String {
            val cipher = Cipher.getInstance(ALGORITHM)
            val keySpec = SecretKeySpec(generateKey(secretKey), "AES")
            val iv = generateIv()
            val ivSpec = IvParameterSpec(iv)

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val encrypted = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

            // IV와 암호화된 텍스트를 결합
            val combined = iv + encrypted

            return Base64.getEncoder().encodeToString(combined)
        }

        fun decrypt(encryptedText: String, secretKey: String): String {
            val cipher = Cipher.getInstance(ALGORITHM)
            val keySpec = SecretKeySpec(generateKey(secretKey), "AES")

            // Base64 디코딩
            val combined = Base64.getDecoder().decode(encryptedText)

            // IV 추출
            val iv = combined.sliceArray(0 until IV_SIZE)
            val ivSpec = IvParameterSpec(iv)

            // 암호화된 데이터 추출
            val encrypted = combined.sliceArray(IV_SIZE until combined.size)

            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            val decrypted = cipher.doFinal(encrypted)

            return String(decrypted, Charsets.UTF_8)
        }

        private fun generateKey(secretKey: String): ByteArray {
            return secretKey.toByteArray(Charsets.UTF_8).copyOf(KEY_SIZE / 8)
        }

        private fun generateIv(): ByteArray {
            val iv = ByteArray(IV_SIZE)
            SecureRandom().nextBytes(iv)
            return iv
        }
    }
}

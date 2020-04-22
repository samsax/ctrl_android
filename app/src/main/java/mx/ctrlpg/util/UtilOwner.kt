package mx.ctrlpg.util

import java.security.MessageDigest

class UtilOwner{

    fun toMD5(value: String): String {
        val bytes = MessageDigest.getInstance("MD5").digest(value.toByteArray())
        return bytes.toHex()
    }

    fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }
}
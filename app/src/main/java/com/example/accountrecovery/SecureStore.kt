package com.example.accountrecovery

import android.content.Context
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class SecureStore(private val context: Context) {
    private val alias = "account_recovery_key"
    private val prefs = context.getSharedPreferences("secure_results", Context.MODE_PRIVATE)
    private fun key(): SecretKey {
        val ks = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        (ks.getKey(alias, null) as? SecretKey)?.let { return it }
        val kg = KeyGenerator.getInstance("AES", "AndroidKeyStore")
        kg.init(android.security.keystore.KeyGenParameterSpec.Builder(alias, android.security.keystore.KeyProperties.PURPOSE_ENCRYPT or android.security.keystore.KeyProperties.PURPOSE_DECRYPT).setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE).build())
        return kg.generateKey()
    }
    fun saveConfirmed(number: String) {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding"); cipher.init(Cipher.ENCRYPT_MODE, key())
        val ct = cipher.doFinal(number.toByteArray(StandardCharsets.UTF_8)); val iv = cipher.iv
        prefs.edit().putString("iv", Base64.encodeToString(iv, Base64.NO_WRAP)).putString("data", Base64.encodeToString(ct, Base64.NO_WRAP)).apply()
    }
    fun readConfirmed(): String? = try {
        val iv = Base64.decode(prefs.getString("iv", null), Base64.NO_WRAP); val data = Base64.decode(prefs.getString("data", null), Base64.NO_WRAP)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding"); cipher.init(Cipher.DECRYPT_MODE, key(), GCMParameterSpec(128, iv)); String(cipher.doFinal(data), StandardCharsets.UTF_8)
    } catch (_: Exception) { null }
}

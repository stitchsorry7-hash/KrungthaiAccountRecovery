package com.example.accountrecovery

import android.content.Context

/** Stores only non-secret connection state. Tokens, passwords, OTPs and API keys are never stored here. */
class AiHubConnectionState(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun isConnected(id: String): Boolean = prefs.getBoolean("connected_$id", false)

    fun markConnected(id: String) {
        prefs.edit().putBoolean("connected_$id", true).apply()
    }

    fun clear(id: String) {
        prefs.edit().remove("connected_$id").apply()
    }

    companion object {
        private const val PREFS = "ai_hub_connection_state"
    }
}

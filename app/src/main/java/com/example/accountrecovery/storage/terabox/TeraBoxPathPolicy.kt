package com.example.accountrecovery.storage.terabox

/** Restricts remote paths to the application's dedicated AccountRecovery tree. */
object TeraBoxPathPolicy {
    private const val ROOT = "/AccountRecovery"
    private val allowedRoots = setOf("project", "evidence", "results", "backup", "sync")

    fun normalize(path: String): String? {
        val raw = path.trim().replace('\\', '/')
        if (raw.isEmpty() || raw.contains("..") || raw.contains("//")) return null
        val normalized = if (raw.startsWith('/')) raw else "/$raw"
        val parts = normalized.removePrefix("/").split('/')
        if (parts.size < 2 || parts[0] != ROOT.removePrefix("/")) return null
        if (parts[1] !in allowedRoots) return null
        if (parts.drop(2).any { it.isBlank() || it == "." }) return null
        return normalized
    }
}

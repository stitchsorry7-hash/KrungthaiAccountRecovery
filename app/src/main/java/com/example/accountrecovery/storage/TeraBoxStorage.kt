package com.example.accountrecovery.storage

/** Boundary for an official TeraBox Open Platform integration. */
interface TeraBoxStorage {
    suspend fun connect(): StorageResult
    suspend fun createRootFolder(): StorageResult
    suspend fun upload(localPath: String, remotePath: String): StorageResult
    suspend fun download(remotePath: String, localPath: String): StorageResult
    suspend fun sync(): StorageResult
    suspend fun status(): StorageResult
}

data class StorageResult(val ok: Boolean, val message: String)

/** Safe default until the user authorizes an official Open Platform integration. */
class TeraBoxNotConfigured : TeraBoxStorage {
    override suspend fun connect() = notConfigured()
    override suspend fun createRootFolder() = notConfigured()
    override suspend fun upload(localPath: String, remotePath: String) = notConfigured()
    override suspend fun download(remotePath: String, localPath: String) = notConfigured()
    override suspend fun sync() = notConfigured()
    override suspend fun status() = notConfigured()
    private fun notConfigured() = StorageResult(false, "TeraBox Open Platform authorization is not configured.")
}

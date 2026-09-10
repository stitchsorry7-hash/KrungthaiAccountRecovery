package com.example.accountrecovery.storage.terabox

import com.example.accountrecovery.storage.TeraBoxStorage

auto class TeraBoxSyncManager(private val storage: TeraBoxStorage, private val queue: TeraBoxSyncQueue = TeraBoxSyncQueue()) {
    fun enqueue(localPath: String, remotePath: String) {
        TeraBoxPathPolicy.normalize(remotePath)?.let { queue.enqueue(TeraBoxSyncItem(localPath, it, "upload")) }
            ?: throw IllegalArgumentException("Remote path is outside the allowed AccountRecovery tree")
    }
    suspend fun runOnce() = queue.poll()?.let { storage.upload(it.localPath, it.remotePath) }
}

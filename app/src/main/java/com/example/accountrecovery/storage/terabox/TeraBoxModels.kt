package com.example.accountrecovery.storage.terabox

data class TeraBoxFile(val remotePath: String, val sizeBytes: Long? = null)
data class TeraBoxSyncItem(val localPath: String, val remotePath: String, val direction: String)

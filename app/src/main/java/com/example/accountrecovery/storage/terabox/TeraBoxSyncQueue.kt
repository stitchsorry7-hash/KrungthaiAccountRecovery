package com.example.accountrecovery.storage.terabox

class TeraBoxSyncQueue {
    private val items = ArrayDeque<TeraBoxSyncItem>()
    fun enqueue(item: TeraBoxSyncItem) { items.addLast(item) }
    fun poll(): TeraBoxSyncItem? = if (items.isEmpty()) null else items.removeFirst()
    fun size(): Int = items.size
}

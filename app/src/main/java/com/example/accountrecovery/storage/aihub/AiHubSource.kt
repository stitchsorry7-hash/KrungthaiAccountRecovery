package com.example.accountrecovery.storage.aihub

/** Unified source types exposed to the AI Hub. */
enum class AiHubSource { LOCAL_FILES, TERABOX, GITHUB }

data class AiHubFile(
    val source: AiHubSource,
    val id: String,
    val name: String,
    val mimeType: String? = null,
    val sizeBytes: Long? = null,
    val uri: String? = null
)

data class AiHubSearchResult(
    val source: AiHubSource,
    val items: List<AiHubFile>,
    val error: String? = null
)

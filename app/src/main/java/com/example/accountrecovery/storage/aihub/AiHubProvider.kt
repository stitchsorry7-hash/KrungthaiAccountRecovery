package com.example.accountrecovery.storage.aihub

interface AiHubProvider {
    val source: AiHubSource
    suspend fun search(query: String): AiHubSearchResult
}

/** Local files provider is intentionally UI/SAF driven; it never bypasses Android permissions. */
class LocalFilesProvider : AiHubProvider {
    override val source = AiHubSource.LOCAL_FILES
    override suspend fun search(query: String) =
        AiHubSearchResult(source, emptyList(), "Use Android Storage Access Framework to grant a folder/file.")
}

/** TeraBox adapter boundary. Implement with the user's authorized TeraBox API/SDK. */
class TeraBoxProvider : AiHubProvider {
    override val source = AiHubSource.TERABOX
    override suspend fun search(query: String) =
        AiHubSearchResult(source, emptyList(), "TeraBox authorization/API configuration required.")
}

/** GitHub adapter boundary. Keep tokens out of source; use the connected GitHub integration/server. */
class GitHubProvider : AiHubProvider {
    override val source = AiHubSource.GITHUB
    override suspend fun search(query: String) =
        AiHubSearchResult(source, emptyList(), "GitHub authorization/repository configuration required.")
}

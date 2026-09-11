package com.example.accountrecovery.storage.aihub

interface AiHubProvider {
    val source: AiHubSource
    suspend fun search(query: String): AiHubSearchResult
}

/** Local files provider is UI/SAF driven; it never bypasses Android permissions. */
class LocalFilesProvider : AiHubProvider {
    override val source = AiHubSource.LOCAL_FILES
    override suspend fun search(query: String) =
        AiHubSearchResult(source, emptyList(), "Use Android Storage Access Framework to grant a folder/file.")
}

/** TeraBox adapter boundary. Implement only with the user's authorized official API/SDK. */
class TeraBoxProvider : AiHubProvider {
    override val source = AiHubSource.TERABOX
    override suspend fun search(query: String) =
        AiHubSearchResult(source, emptyList(), "TeraBox official API/SDK authorization and configuration required.")
}

/** GitHub adapter boundary. Tokens must never be hard-coded into source or APK. */
class GitHubProvider : AiHubProvider {
    override val source = AiHubSource.GITHUB
    override suspend fun search(query: String) =
        AiHubSearchResult(source, emptyList(), "GitHub authorization/repository configuration required.")
}

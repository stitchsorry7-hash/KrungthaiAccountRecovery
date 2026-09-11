package com.example.accountrecovery.storage.aihub

class AiHubManager(
    private val providers: List<AiHubProvider> = listOf(
        LocalFilesProvider(),
        TeraBoxProvider(),
        GitHubProvider()
    )
) {
    suspend fun searchEverywhere(query: String): List<AiHubSearchResult> =
        providers.map { it.search(query) }
}

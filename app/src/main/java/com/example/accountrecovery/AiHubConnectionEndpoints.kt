package com.example.accountrecovery

/** Public destinations only. Secrets/tokens are never stored here. */
data class AiHubConnectionEndpoint(
    val id: String,
    val label: String,
    val description: String,
    val authorizationUrl: String? = null
)

object AiHubConnectionEndpoints {
    val all: List<AiHubConnectionEndpoint> = listOf(
        AiHubConnectionEndpoint("files", "📁 Files", "Android Storage Access Framework; user selects the files/folder."),
        AiHubConnectionEndpoint("terabox", "☁️ TeraBox", "Official developer/API destination; real file CRUD requires authorized TeraBox API/SDK credentials.", "https://teraabox.com/developers"),
        AiHubConnectionEndpoint("github", "🐙 GitHub", "GitHub App/OAuth destination; repository permissions must be explicitly authorized.", "https://github.com/settings/apps"),
        AiHubConnectionEndpoint("poe", "🤖 Poe", "Sign in with Poe OAuth + PKCE; client ID and redirect URI must be registered by the app owner.", "https://poe.com/api/clients"),
        AiHubConnectionEndpoint("chatgpt", "💬 ChatGPT / OpenAI", "Official API/OAuth configuration; credentials must be supplied by the user through a secure flow.", "https://platform.openai.com/"),
        AiHubConnectionEndpoint("gemini", "✨ Gemini / Google", "Official Gemini API/OAuth configuration; credentials must be supplied through a secure flow.", "https://ai.google.dev/"),
        AiHubConnectionEndpoint("google", "🔎 Google", "Google account/API destinations are permission-scoped; no automatic account-data copying." , "https://console.cloud.google.com/"),
        AiHubConnectionEndpoint("gmail", "✉️ Gmail", "Gmail data requires Google OAuth scopes and explicit user consent.", "https://myaccount.google.com/connections"),
        AiHubConnectionEndpoint("chrome", "🌐 Chrome / Web", "Web navigation is delegated to Android/browser; the app does not silently read browser data."),
        AiHubConnectionEndpoint("krungthai", "🏦 Krungthai Open Banking", "Bank APIs require official partner registration, scopes, sandbox/contract approval and user authorization.", "https://developers.krungthai.com/"),
        AiHubConnectionEndpoint("ci", "⚙️ GitHub Actions / Build", "Build/test endpoint; CI can validate and produce APK artifacts when the repository workflow is configured.", "https://github.com/marketplace?type=actions")
    ]
}

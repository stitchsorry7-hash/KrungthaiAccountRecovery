package com.example.accountrecovery.storage.aihub

/** Connection state is explicit: configured is not the same as authorized or verified. */
enum class AiHubConnectionState { NOT_CONNECTED, READY_TO_CONNECT, CONNECTED, ERROR }

data class AiHubConnection(
    val source: AiHubSource,
    val state: AiHubConnectionState,
    val detail: String
)

object AiHubConnectionCatalog {
    fun defaults() = listOf(
        AiHubConnection(AiHubSource.LOCAL_FILES, AiHubConnectionState.READY_TO_CONNECT, "เลือกไฟล์/โฟลเดอร์ด้วย Android SAF"),
        AiHubConnection(AiHubSource.TERABOX, AiHubConnectionState.READY_TO_CONNECT, "ต้องใช้ Official API/SDK และสิทธิ์ของบัญชี"),
        AiHubConnection(AiHubSource.GITHUB, AiHubConnectionState.READY_TO_CONNECT, "ใช้ GitHub App/OAuth หรือ token แบบสิทธิ์เท่าที่จำเป็น"),
        AiHubConnection(AiHubSource.POE, AiHubConnectionState.READY_TO_CONNECT, "ใช้ Sign in with Poe OAuth + PKCE"),
    )
}

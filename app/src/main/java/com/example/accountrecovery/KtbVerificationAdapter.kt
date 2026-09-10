package com.example.accountrecovery

interface KtbVerificationAdapter {
    suspend fun verifyAccountNumber(number: String): Result<Boolean>
}

/** Safe default: no bank network access and no authentication bypass. */
class NotConfiguredKtbVerificationAdapter : KtbVerificationAdapter {
    override suspend fun verifyAccountNumber(number: String): Result<Boolean> =
        Result.failure(IllegalStateException("Official Krungthai verification is not configured."))
}

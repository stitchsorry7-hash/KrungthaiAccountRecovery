package com.example.accountrecovery

class AccountNumberDetector {
    private val regex = Regex("(?<!\\d)(?:\\d[ -]?){9}\\d(?!\\d)")
    private val ktb = listOf("กรุงไทย", "krungthai", "ktb")
    private val account = listOf("เลขที่บัญชี", "เลขบัญชี", "account no", "account number", "a/c", "บัญชี")
    private val bad = listOf("โทร", "phone", "mobile", "เบอร์", "วันที่", "date")

    fun detect(text: String, source: String = "OCR"): List<AccountCandidate> {
        val normalized = normalizeThaiDigits(text)
        return regex.findAll(normalized).map { m ->
            val number = m.value.filter(Char::isDigit)
            val start = maxOf(0, m.range.first - 90); val end = minOf(normalized.length, m.range.last + 91)
            val ctx = normalized.substring(start, end).replace("\\n".toRegex(), " ").trim()
            var score = 40
            if (ktb.any { ctx.contains(it, ignoreCase = true) }) score += 30
            if (account.any { ctx.contains(it, ignoreCase = true) }) score += 25
            if (bad.any { ctx.contains(it, ignoreCase = true) }) score -= 25
            AccountCandidate(number, score.coerceIn(0, 100), ctx, source)
        }.distinctBy { it.number }.sortedByDescending { it.score }.toList()
    }

    private fun normalizeThaiDigits(s: String): String {
        val thai = "๐๑๒๓๔๕๖๗๘๙"; val arabic = "0123456789"
        return s.map { c -> val i = thai.indexOf(c); if (i >= 0) arabic[i] else c }.joinToString("")
    }
}

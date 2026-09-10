package com.example.accountrecovery

import org.junit.Assert.*
import org.junit.Test

class AccountNumberDetectorTest {
    @Test fun findsThaiDigitsWithKtbContext() {
        val r=AccountNumberDetector().detect("ธนาคารกรุงไทย เลขที่บัญชี ๑๒๓-๔๕๖-๗๘๙๐")[0]
        assertEquals("1234567890", r.number); assertTrue(r.score >= 70)
    }
    @Test fun prefersAccountContextOverPhoneContext() {
        val r=AccountNumberDetector().detect("โทร 0812345678 เลขบัญชี 1234567890 กรุงไทย").first()
        assertEquals("1234567890", r.number)
    }
}

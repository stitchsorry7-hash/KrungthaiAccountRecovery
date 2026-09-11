# AI Hub — TeraBox + GitHub + Files

AI Hub เป็นชั้นกลางเดียวสำหรับค้นหา/อ่านไฟล์จากแหล่งที่ผู้ใช้อนุญาต แล้วส่งผลที่คัดเลือกให้ AI ใช้ต่อ

## Sources
- Local Files: Android Storage Access Framework (SAF); ใช้เฉพาะไฟล์/โฟลเดอร์ที่ผู้ใช้อนุญาต
- TeraBox: ผ่าน API/SDK หรือ connector ที่ผู้ใช้อนุญาตเท่านั้น
- GitHub: ผ่าน OAuth/App/connector ที่ได้รับสิทธิ์; จำกัดตาม repository/permission
- Poe/AI: เป็นชั้นประมวลผลแยกจาก storage; ไม่ได้รับสิทธิ์โดยอัตโนมัติ

## Flow
User → AI Hub → Source adapters → Search/Read → Permission filter → Context pack → AI model → Answer

## Security
- ไม่เก็บ password/OTP ใน source
- ไม่ฝัง API key ใน APK
- ไม่อ่านข้อมูล private ที่ไม่มีสิทธิ์
- บันทึก audit เฉพาะ metadata ที่จำเป็น
- ให้ผู้ใช้เลือก source ก่อนอ่าน/ส่งข้อมูลให้ AI

## Current implementation
`AiHubSource`, `AiHubFile`, `AiHubProvider`, and `AiHubManager` provide the integration boundary. TeraBox and GitHub providers intentionally return a configuration-required state until authorized credentials/connectors are configured.

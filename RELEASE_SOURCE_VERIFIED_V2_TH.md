# Release Source — VERIFIED v2

สถานะอ้างอิงของแพ็กเกจล่าสุดสำหรับโครงการ Account Recovery + TeraBox

- Package: `AccountRecovery_TeraBox_FINAL_2026-09-11_VERIFIED_v2.zip`
- SHA-256: `9480fc9ed8626648c774c90c8ceb26c9de0d5669c4d652481ae61c4275ce39b2`
- Scope: Android Recovery, owner-held evidence search, KTB verification adapter, TeraBox storage/sync boundary, Access Hub, tests and GitHub build workflow.

## Security boundary

ระบบไม่เก็บหรือฝัง OTP, PIN, password, CVV, API key หรือ client secret ใน source/package และไม่ bypass การยืนยันตัวตนของ Google, Krungthai หรือธนาคารอื่น

## External completion gates

1. Build source package บน GitHub Actions/Android Studio และตรวจว่า `assembleDebug` สำเร็จ
2. ติดตั้ง APK และทดสอบ Access Hub, file picker และการแชร์หลักฐาน
3. หากต้องการ TeraBox cloud API จริง ต้องอนุญาตผ่านช่องทาง Open Platform อย่างเป็นทางการ และเก็บ token ใน secure storage
4. การกู้ Gmail และข้อมูลธนาคารจริงต้องผ่านช่องทางยืนยันตัวตนของเจ้าของบัญชี

เอกสารนี้เป็น release manifest เท่านั้น ไม่ใช่การยืนยันว่า APK ล่าสุดถูก build แล้ว

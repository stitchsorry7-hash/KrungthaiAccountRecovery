# AI Hub Integration Status

## เป้าหมาย
รวม Files, TeraBox, GitHub และผู้ให้บริการ AI (Poe / ChatGPT / Gemini) ผ่านชั้น AI Hub เดียว โดยใช้เฉพาะข้อมูลและสิทธิ์ที่เจ้าของบัญชีอนุญาต

## สิ่งที่ทำแล้ว
- มีปุ่ม AI Hub ในหน้าหลักของแอป
- มีหน้า Connections สำหรับ Files / TeraBox / GitHub / Poe / ChatGPT / Gemini
- Files ใช้ Android Storage Access Framework และเก็บ URI permission ที่ผู้ใช้เลือก
- มี provider boundary สำหรับ Local Files / TeraBox / GitHub และ model provider สำหรับ Poe / ChatGPT / Gemini

## สิ่งที่ยังต้องอนุญาตหรือกำหนดค่า
- TeraBox: Official API/SDK + credential ของบัญชีที่ผู้ใช้อนุญาต
- GitHub: GitHub App/OAuth หรือ token ที่มีสิทธิ์น้อยที่สุดเท่าที่งานต้องใช้
- Poe: Sign in with Poe OAuth + PKCE และ client registration
- ChatGPT/OpenAI: official API/OAuth configuration ตามบริการที่ใช้งาน
- Gemini: Google Gemini API/OAuth configuration

## หลักการความปลอดภัย
- ไม่คัดลอก password, OTP, PIN, API secret หรือ credential ลง source code
- ไม่พยายามข้ามการยืนยันตัวตนของธนาคารหรือบริการภายนอก
- ข้อมูลส่วนตัว/ข้อมูลธนาคารต้องมาจากแหล่งที่ผู้ใช้ยืนยันสิทธิ์แล้วเท่านั้น
- การเชื่อมต่อสำเร็จต้องแสดงสถานะแยกจากคำว่า "พร้อมตั้งค่า" และ "รออนุญาต"
- TeraBox endpoint ที่ไม่อยู่ในเอกสารทางการห้ามเดา/สร้างขึ้นเอง

## ขั้นถัดไป
1. ทำ connection state แบบ persisted
2. ทำ OAuth/PKCE callback ของ Poe และ GitHub App/OAuth โดยไม่ฝัง secret
3. ทำ secure credential storage ด้วย Android Keystore
4. ทำ adapters สำหรับ search/read/upload/download เฉพาะ API ที่ได้รับอนุญาต
5. เพิ่ม integration tests และ CI build
6. ตรวจ APK ก่อนติดตั้งจริง

> การมีปุ่มหรือ adapter ในแอปไม่ได้หมายความว่าแอปเข้าถึงข้อมูลส่วนตัวของบริการนั้นแล้ว ต้องผ่าน authorization ของเจ้าของบัญชีทุกครั้ง

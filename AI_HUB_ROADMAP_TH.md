# AI Hub — Roadmap การรวมระบบ

## เป้าหมาย
รวมแหล่งข้อมูลและผู้ให้บริการ AI ที่ผู้ใช้อนุญาตไว้ในแอป Android เดียว โดยใช้ adapter มาตรฐานและสิทธิ์แบบ least privilege

## โครงสร้างที่มีแล้ว
- Local Files / Android Storage Access Framework
- TeraBox adapter boundary
- GitHub adapter
- Poe adapter
- ChatGPT / OpenAI adapter
- Gemini adapter
- Google / Gmail authorization endpoints
- Chrome / Web handoff
- Krungthai Open Banking endpoint boundary
- GitHub Actions build/test

## งานที่ต้องทำก่อนเรียกว่าเชื่อมต่อจริง
1. OAuth callback + PKCE สำหรับผู้ให้บริการที่รองรับ
2. Secure credential/token storage ด้วย Android Keystore/Encrypted storage
3. TeraBox official API/SDK credentials and approved scopes
4. Google/Gmail/Gemini OAuth scopes and callback
5. GitHub least-privilege authorization
6. AI Router ที่เลือก provider ตามงานและเก็บ source attribution
7. Unified normalized search/index
8. Sync conflict/error handling
9. Integration tests และ health checks
10. Build APK และติดตั้งโดยผู้ใช้บน Android

## ขอบเขตความปลอดภัย
แอปไม่คัดลอกหรือเปิดเผย password, OTP, PIN, API key, bank credentials หรือข้อมูล app-private ของบริการอื่นโดยอัตโนมัติ และจะเข้าถึงข้อมูลเฉพาะที่ผู้ใช้อนุญาตผ่าน API/OAuth/Android permission ที่ถูกต้อง

## นิยามการนำความสามารถ AI มาใช้
ความสามารถของ Poe/ChatGPT/Gemini และบริการอื่นจะรวมผ่าน provider adapters/AI Router ในแอป ไม่ใช่การแก้ไขหรือย้ายโค้ดภายในแอปของบุคคลที่สาม

# AI Hub Integration — implementation plan

เป้าหมาย: รวมแหล่งข้อมูลที่ผู้ใช้อนุญาตเข้ากับแอป Android ผ่านชั้นกลางเดียว โดยไม่ข้ามระบบยืนยันตัวตนและไม่ฝัง secret ลง APK

## Sources
- Local Files: Android Storage Access Framework (SAF)
- TeraBox: official API/SDK only
- GitHub: GitHub App/OAuth/token with least privilege
- Poe: Sign in with Poe OAuth 2.0 + PKCE
- ChatGPT/OpenAI: official API/OAuth flow only
- Gemini: official Google API/OAuth flow only

## Data flow
User permission -> Connection Adapter -> Secure credential store -> Source adapter -> normalized records -> AI Hub index -> selected AI provider -> result + source attribution

## Rules
1. Never copy passwords, OTP, PINs, API secrets, or private banking credentials into source code.
2. Never infer or bypass a bank account number or banking authentication.
3. Files are accessible only after the user selects a file/folder through Android's picker.
4. A provider is CONNECTED only after successful authorization/configuration; a UI button alone is not a connection.
5. TeraBox endpoints are not invented; implementation waits for the official API/SDK contract and authorized credentials.
6. AI providers receive only the minimum data needed for the current task.
7. Add source labels to imported records so data can be traced back to the authorized source.

## Current implementation status
- AI Hub entry point exists in MainActivity.
- Connections screen exists for Files/TeraBox/GitHub/Poe/ChatGPT/Gemini.
- Local Files uses ACTION_OPEN_DOCUMENT_TREE and persisted URI permission.
- Provider adapters exist as boundaries; real cloud access requires authorization/configuration.

## Next build gates
- Add secure connection-state persistence.
- Add OAuth callback activities for providers that support OAuth.
- Add provider adapters and health checks.
- Add normalized search/index layer.
- Add integration/unit tests for connected, denied, expired, and offline states.
- Run CI and publish a debug APK artifact only after the build passes.

## Installation
This repository change does not silently install an APK on a physical Android device. After CI produces a signed/appropriate debug APK, the user can install it explicitly on their device.

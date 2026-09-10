# Completion Checklist — Account Recovery + TeraBox

## Completed in source/package
- Android recovery UI and Access Hub
- Owner-held evidence file selection and sharing
- Account-number candidate detection from evidence
- KTB verification boundary
- Gmail/Google official recovery entry point
- All-bank recovery guidance using owner-held evidence
- TeraBox adapter/sync boundary
- Security rules: no OTP/PIN/password/CVV/API key/client secret in source
- GitHub Actions workflow for validation, tests and debug build

## External gates — cannot be completed by source changes alone
- GitHub Actions must actually execute and produce a debug APK artifact
- APK must be installed and tested on the target Android device
- TeraBox cloud API requires official account authorization/OAuth and approved API access
- Gmail recovery requires Google identity verification
- Bank account recovery/verification requires the bank's official authentication process

## Release truth rule
Do not mark an external gate complete without its actual evidence (CI result, APK artifact/install test, or official authorization result).

## Current VERIFIED v2 package
`AccountRecovery_TeraBox_FINAL_2026-09-11_VERIFIED_v2.zip`

SHA-256:
`9480fc9ed8626648c774c90c8ceb26c9de0d5669c4d652481ae61c4275ce39b2`

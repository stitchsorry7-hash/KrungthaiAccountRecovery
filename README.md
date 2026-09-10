# KrungthaiAccountRecovery

## Build status
Android CI is configured to run validation, unit tests, and a debug APK build on every push, pull request, or manual workflow dispatch.

## Release verification checkpoint — 2026-09-11
The source/package verification is complete. The latest release APK is **not** considered verified until GitHub Actions completes successfully and publishes the `account-recovery-debug-apk` artifact.

The recovery workflow is limited to owner-held evidence and official verification channels. It does not bypass OTP/PIN/password controls or perform universal account-number lookup from a phone number or national ID.

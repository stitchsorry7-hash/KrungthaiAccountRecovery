#!/usr/bin/env bash
set -euo pipefail
root="$(cd "$(dirname "$0")" && pwd)"
required=(
  settings.gradle.kts
  build.gradle.kts
  app/build.gradle.kts
  app/src/main/AndroidManifest.xml
  app/src/main/java/com/example/accountrecovery/MainActivity.kt
  app/src/main/java/com/example/accountrecovery/AccountNumberDetector.kt
  app/src/main/java/com/example/accountrecovery/SecureStore.kt
  app/src/main/java/com/example/accountrecovery/KtbVerificationAdapter.kt
  app/src/main/java/com/example/accountrecovery/storage/TeraBoxStorage.kt
)
for f in "${required[@]}"; do test -f "$root/$f" || { echo "MISSING: $f"; exit 1; }; done
python3 - <<'PY'
import xml.etree.ElementTree as ET
ET.parse('app/src/main/AndroidManifest.xml')
print('XML PASS')
PY
# Check for likely hard-coded credential assignments, not safe API/class identifiers such as SecretKey.
if grep -RniE '(password|passwd|api[_-]?key|access[_-]?token|refresh[_-]?token|otp|pin)[[:space:]]*[:=]' app/src/main 2>/dev/null; then
  echo 'POSSIBLE HARDCODED CREDENTIAL FOUND'
  exit 1
fi
echo 'STATIC PROJECT CHECK PASS'

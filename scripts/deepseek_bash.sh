#!/usr/bin/env bash

# Set error handling
set -euo pipefail

# Create logs directory
mkdir -p build/logs

echo "=== Building Release APK ==="

# Run Gradle build and capture output
{
  cd android
  ./gradlew clean assembleRelease \
    --no-daemon \
    --stacktrace \
    --info \
    --scan \
    --console=plain 2>&1 | tee ../build/logs/gradle.log
  cd ..
} || {
  echo "Gradle build failed! Showing errors:"
  grep -A 10 -B 5 -i "error\|fail\|exception" build/logs/gradle.log || cat build/logs/gradle.log
  exit 1
}

# Run Flutter build and capture output
{
  flutter build apk --release --verbose 2>&1 | tee build/logs/flutter.log
} || {
  echo "Flutter build failed! Showing errors:"
  grep -A 10 -B 5 -i "error\|fail\|exception" build/logs/flutter.log || cat build/logs/flutter.log
  exit 1
}

echo "=== Build completed successfully ==="
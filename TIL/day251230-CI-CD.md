# CI / CD

```yaml
      # 1. 모든 flavor의 debug APK를 빌드합니다. (dev, qa, prod)
      - name: Build all debug APKs
        run: ./gradlew assembleDebug

      # 2. 생성된 모든 debug APK들을 업로드합니다.
      - name: Upload All Debug APKs
        uses: actions/upload-artifact@v4
        with:
          name: all-debug-apks # 원래 이름 유지
          path: app/build/outputs/apk/**/debug/*.apk # 이 경로는 여기서 유효함

```


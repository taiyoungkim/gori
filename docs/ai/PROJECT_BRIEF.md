# PROJECT_BRIEF
- 스택: Kotlin, Compose(Material3), Hilt, Room, Media3/ExoPlayer
- 빌드: Gradle KTS
- 스타일: ktlint(or ktfmt), detekt
- 테스트: junit5(이관 진행), mockk, 일부 robolectric
- 도메인: 뷰어/오디오/AI-TTS/다운로드 워커/DRM 토큰/라이선스
- 위험구역: TTS/오디오 동시성, DRM 만료, 대용량 IO
- 금지: 비밀 하드코딩, 과한 디버그 로그, UI 스레드 블로킹

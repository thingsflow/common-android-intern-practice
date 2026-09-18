> **유휴 추정.** 마지막 푸시가 2022-06-23이고 그 뒤 커밋이 없다.
> 아래 내용은 그 시점의 기록으로 읽는다. 현재 동작을 보장하지 않는다.
> 근거: GitHub REST API pushed_at (2026-09-19 조회).

# common-android-intern-practice

띵스플로우 안드로이드 인턴십 실습용 Kotlin 애플리케이션의 기록이다. 단일 `app` 모듈에 Android 화면, ViewModel 기반 구조, 데이터 바인딩·뷰 바인딩 기반 클래스, 목록 UI 유틸리티가 들어 있다.

## 먼저 읽기

저장소 작업 규칙은 [AGENTS.md](AGENTS.md), 현재 구조와 실측값은 [structure/INDEX.md](structure/INDEX.md), 앱 영역의 상세 경계는 [structure/app.md](structure/app.md)에서 확인한다.

## 확인된 도구와 명령

- Gradle Wrapper: `./gradlew` (`gradle/wrapper/gradle-wrapper.properties`, 2026-09-19 읽음)
- 디버그 빌드 태스크: `./gradlew assembleDebug` (`app/build.gradle`, 2026-09-19 읽음)
- 단위 테스트 태스크: `./gradlew test` (`app/build.gradle`, 2026-09-19 읽음)
- Kotlin 포맷 검사 태스크: `./gradlew ktlint` (`ktlint.gradle`, 2026-09-19 읽음)

위 명령은 설정 파일에서 확인한 진입점이다. 이 스캐폴드 작업에서는 의존성 설치·빌드·테스트를 실행하지 않았다. `settings.gradle`이 현재 트리에 없는 `app:common:android-architecture-kit`과 `app:common:android-list-kit`도 포함하므로 실행 가능 여부는 확인되지 않았다.

# AGENTS.md

## 이 저장소가 하는 일

이 저장소는 Android 인턴십 실습을 위한 Kotlin 애플리케이션 기록이며, 앱 화면과 ViewModel 뼈대, Android 공통 UI·아키텍처 기반 클래스를 담는다. 배포 서비스나 다중 애플리케이션 모노레포로 확인되지는 않는다.

## 읽는 순서

1. 이 `AGENTS.md`에서 작업 규칙을 읽는다.
2. `structure/INDEX.md`에서 스택, 진입점, 규약, PR 흐름을 확인한다.
3. `app` 아래를 바꿀 때는 `structure/app.md`에서 경계와 의존을 확인한다.

## 저장소 구조

- `app/`: Android 애플리케이션 소스, 리소스, 테스트와 모듈 빌드 설정을 소유한다.
- `gradle/`: Gradle Wrapper 바이너리와 배포 설정을 소유한다.
- 루트 Gradle 파일: 플러그인·저장소·버전·모듈 포함 관계·포맷 태스크를 소유한다.
- `structure/`: 현재 구조의 사람이 읽는 인덱스와 기계용 manifest를 소유한다.

## 작업 규칙

- 기본 브랜치는 `main`이며, 작업 브랜치에서 변경한다. 원격 브랜치 5개 중 `feature/` 접두사는 3개다 (2026-09-19 실측).
- 커밋 메시지는 최근 6개 중 `fix:` 3개, `feat:` 1개, 접두사 없음 2개다. 새 커밋은 변경 성격을 드러내는 접두사를 사용한다 (2026-09-19 실측).
- PR은 변경 목적과 검증 범위를 적고 리뷰 결과를 반영한다. 머지된 PR 25건에는 승인 19건, 변경 요청 17건, 일반 코멘트 8건이 기록돼 있다 (2026-09-19 조회).
- Kotlin 코드는 `ktlint.gradle`의 `ktlint` 태스크를 기준으로 포맷을 확인한다.
- Android 빌드·단위 테스트 진입점은 각각 `./gradlew assembleDebug`, `./gradlew test`다. 현재 트리에 없는 모듈 참조가 있으므로 실행 전 `settings.gradle`과 실제 모듈 경로의 일치를 확인한다.

## Code Review Rules

- `MainActivity`에서 시작하는 화면 흐름과 `AndroidManifest.xml`의 launcher 선언을 함께 검토한다.
- ViewModel 생명주기 정리 규약을 바꾸면 `Cleaner`, `BaseViewModel`, 데이터 바인딩·뷰 바인딩 기반 클래스의 호출 관계를 함께 검토한다.
- 목록 어댑터 계약을 바꾸면 `AutoBindHolderFactory`, `AutoBindListAdapter`, `AutoBindViewHolder`, `AutoBindViewPagerAdapter`의 타입 관계를 함께 검토한다.
- 리소스 ID를 바꾸면 참조하는 Kotlin 파일과 `app/src/main/res/` XML을 같은 변경에서 검토한다.
- `settings.gradle`에 포함한 모듈은 실제 트리에 존재해야 한다.

## 하지 않는 것

- 확인되지 않은 빌드·테스트 성공을 문서에 사실로 적지 않는다.
- `base/architecture`의 생명주기 계약을 확인하지 않고 하위 화면 기반 클래스를 바꾸지 않는다.
- 생성물이나 로컬 자격증명을 커밋하지 않는다.
- 현재 트리에 없는 모듈을 존재하는 것처럼 문서화하지 않는다.
- 근거 없는 배포 절차나 브랜치 보호 규칙을 만들지 않는다.

## 근거

- `settings.gradle`, `build.gradle`, `versions.gradle`, `app/build.gradle`, `ktlint.gradle` (2026-09-19 읽음)
- `app/src/main/AndroidManifest.xml`, `app/src/main/java/`, `app/src/main/res/` (2026-09-19 읽음)
- `git log -100 --pretty=format:%s`, `git branch -r`, GitHub REST API와 `gh pr list` (2026-09-19 조회)
- 기존 `AGENTS.md`와 `CLAUDE.md`는 없어서 흡수한 규칙이 없다 (2026-09-19 확인).

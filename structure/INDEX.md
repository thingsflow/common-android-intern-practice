# 구조 인덱스

## 한 줄 요약

Kotlin과 AndroidX로 만든 인턴십 실습용 단일 Android 앱이며, 화면 뼈대와 재사용 가능한 생명주기·바인딩·목록 UI 기반 코드를 함께 보관한다.

## 스택

- 언어: Kotlin 1.4.10 (`versions.gradle`, 2026-09-19 읽음)
- 런타임·플랫폼: Android, min SDK 25, target/compile SDK 32 (`app/build.gradle`, 2026-09-19 읽음)
- 빌드: Gradle Wrapper 7.2와 Android Gradle Plugin 4.1.3 (`gradle/wrapper/gradle-wrapper.properties`, `build.gradle`, 2026-09-19 읽음)
- UI·상태: AndroidX AppCompat, Fragment, Lifecycle, Navigation, Data Binding, View Binding, RecyclerView, ViewPager2 (`app/build.gradle`과 `app/src/main/java/`, 2026-09-19 읽음)
- 비동기·주입: Kotlin Coroutines, RxJava 3, Hilt 의존성이 선언돼 있다 (`app/build.gradle`, 2026-09-19 읽음).
- 패키지 관리자: Gradle (`gradlew`, `settings.gradle`, 2026-09-19 읽음)

## 기본 브랜치와 배포

- GitHub 기본 브랜치는 `main`이다 (GitHub REST API, 2026-09-19 조회).
- `.github/workflows/`와 최근 Actions 실행 20건이 모두 0건이므로 확인된 CI·배포 트리거는 없다 (`find .github`, `gh run list`, 2026-09-19 조회).
- GitHub Actions 권한과 배포 연결 화면은 직접 URL에서 404가 반환되어 `(미확인)`이다 (Aside 읽기 전용 1회 시도, 2026-09-19).

## 디렉터리

- `app/`: 앱 빌드 설정과 ProGuard 규칙을 둔다.
  - `app/src/main/java/`: launcher Activity, 주 화면, ViewModel, 공통 아키텍처와 목록 UI 코드를 둔다.
  - `app/src/main/res/`: 레이아웃, 테마, 색상, 문자열, launcher 리소스를 둔다.
  - `app/src/test/`, `app/src/androidTest/`: 각각 JVM 단위 테스트와 Android 계측 테스트 예제를 둔다.
- `gradle/wrapper/`: Gradle 7.2 Wrapper 설정과 바이너리를 둔다.
- `structure/`: 이 인덱스, 앱 영역 상세 문서, 기계용 manifest를 둔다.

## 진입점

- 앱 시작: `app/src/main/AndroidManifest.xml`의 launcher `MainActivity`; `MainActivity`가 `MainFragment`를 `main_activity` 컨테이너에 붙인다 (2026-09-19 읽음).
- 빌드: `./gradlew assembleDebug` (`app/build.gradle`, 2026-09-19 읽음, 실행하지 않음)
- 단위 테스트: `./gradlew test` (`app/build.gradle`, 2026-09-19 읽음, 실행하지 않음)
- 계측 테스트: `./gradlew connectedAndroidTest` (`app/build.gradle`, 2026-09-19 읽음, 실행하지 않음)
- 포맷 검사: `./gradlew ktlint` (`ktlint.gradle`, 2026-09-19 읽음, 실행하지 않음)
- `settings.gradle`이 현재 트리에 없는 두 모듈을 포함하므로 위 Gradle 명령의 현재 성공 여부는 `(미확인)`이다.

## 내부 설계

앱의 경계와 데이터 흐름은 [app.md](app.md)를 먼저 읽는다. `MainActivity`가 Android 진입점이고 `MainFragment`가 화면을 소유한다. `base/architecture`는 이벤트, ViewModel 정리, 데이터 바인딩·뷰 바인딩 기반 클래스를 제공하며, `base/ui/list`는 RecyclerView와 ViewPager2용 어댑터·뷰 기반 코드를 제공한다. 현재 `MainViewModel`에는 상태나 외부 데이터 연동 구현이 없다 (`app/src/main/java/com/thingsflow/internapplication/ui/main/MainViewModel.kt`, 2026-09-19 읽음).

## 규약(실측)

- 원격 브랜치 5개 중 접두사는 `feature/` 3개, 접두사 없음 2개다 (`git branch -r`, 2026-09-19 조회).
- 전체 6커밋의 메시지 접두사는 `fix` 3개, `feat` 1개, 접두사 없음 2개다 (`git log -100 --pretty=format:%s`, 2026-09-19 조회).
- `.github/CODEOWNERS`, PR 템플릿, 이슈 템플릿은 0개다 (`find .github`, 2026-09-19 확인).
- 워크플로 파일과 최근 Actions 실행은 각각 0개다 (`find .github`, `gh run list --limit 20`, 2026-09-19 조회).

## PR 흐름(실측)

- 머지된 PR은 25건이다. base 분포는 `dayuncha` 9건, `minsuh` 6건, `feature/dy-onboarding2` 4건, `feature/ms-onboarding2` 4건, `hyunjeong` 2건이다 (`gh pr list --state merged --limit 30`, 2026-09-19 조회).
- base←head 쌍은 22종이다. 반복된 쌍은 `dayuncha`←`feature/dy-hilt` 2건, `dayuncha`←`feature/dy-navigation` 2건, `minsuh`←`feature/ms-mvvm` 2건이며 나머지 19종은 각 1건이다 (같은 조회).
- 25건에 기록된 리뷰는 승인 19건, 변경 요청 17건, 일반 코멘트 8건이다 (같은 조회).
- 저장소 설정상 merge commit, squash merge, rebase merge가 모두 허용돼 있다 (GitHub REST API와 설정 화면 재확인, 2026-09-19 조회).
- `main` 보호 API는 HTTP 404였고 ruleset은 0개이므로 브랜치 보호와 필수 검사는 없다. 필수 승인자 수도 0명이다 (GitHub REST API, 2026-09-19 조회).

## 흡수한 문서

기존 README, AGENTS, CLAUDE, `docs/`, DESIGN, ARCHITECTURE 문서가 없어 흡수한 문서는 0개다 (추적 파일 목록, 2026-09-19 확인).

## 이관 기록

기존 README가 없어 이관 0건, legacy 보존 0건이다 (2026-09-19 확인).

## 다른 저장소와 어떻게 이어지나

확인된 사내 저장소 관계 없음 (워크플로 overlay·워크플로 교차 체크아웃·매니페스트 깃 URL·서브모듈·코드와 실행 설정의 사내 호스트 호출, 2026-09-19 조회).

## 근거

- 트리·코드: `git ls-files`, `app/src/main/AndroidManifest.xml`, `app/src/main/java/`, `app/src/main/res/` (2026-09-19 읽음)
- 빌드·의존: `build.gradle`, `app/build.gradle`, `versions.gradle`, `settings.gradle`, `ktlint.gradle`, `gradle/wrapper/gradle-wrapper.properties` (2026-09-19 읽음)
- 측정: `measure.py` 결과 추적 파일 64개, 심볼릭 링크 제외 0개, `app` 코드 파일 27개·1,268줄 (2026-09-19 실행)
- GitHub: 저장소 REST API, `branches/main/protection`, `rulesets`, `gh pr list`, `gh run list` (2026-09-19 조회)
- API 밖 설정: `https://github.com/thingsflow/common-android-intern-practice/settings/actions`, `/settings/branches`, `/settings/environments`, `/settings/deployments`가 로그인된 읽기 전용 확인에서 404여서 `(미확인)`이다 (Aside 1회 시도, 2026-09-19).
- 추적 파일명과 내용의 시크릿 징후를 경로만 검사했으며 발견 0건이다 (`git ls-files`, `git grep -IlE`, 2026-09-19 실행).

## 티어C 정리로 흡수한 문서 (2026-09-19)

사양은 티어 C가 structure/INDEX.md 한 장이다. 아래 문서들이 별도 파일로
있었고 내용을 여기로 옮긴 뒤 파일을 지웠다. 버린 내용은 없다.

### 원래 structure/app.md

## app 영역

### 경계

`app/`은 Android 애플리케이션의 manifest, Kotlin 코드, 리소스, 테스트와 모듈 빌드 설정을 소유한다. 루트 Gradle 플러그인·저장소·공통 버전과 Wrapper 배포 설정은 소유하지 않는다.

### 구성

- 전체: 코드 파일 27개, 물리적 코드 1,268줄 (`measure.py`, 2026-09-19 실행)
- `app/src/main/java/com/thingsflow/internapplication/`: `MainActivity`와 앱 패키지 루트, Kotlin 파일 1개.
- `app/src/main/java/com/thingsflow/internapplication/ui/main/`: 주 화면 Fragment와 ViewModel, Kotlin 파일 2개.
- `app/src/main/java/com/thingsflow/internapplication/base/architecture/`: 이벤트·생명주기 정리·바인딩·리소스 변환 기반 코드, Kotlin 파일 15개.
- `app/src/main/java/com/thingsflow/internapplication/base/ui/list/`: 목록 어댑터·장식·무한 ViewPager2 기반 코드, Kotlin 파일 7개.
- `app/src/main/res/`: 레이아웃 4개, values XML 4개, launcher drawable/mipmap 리소스를 둔다.
- `app/src/test/`, `app/src/androidTest/`: 테스트 Kotlin 파일을 각 1개 둔다.

### 데이터 흐름

Android launcher intent가 `MainActivity.onCreate`로 들어오고, 저장 상태가 없으면 `MainFragment.newInstance()`를 `R.id.container`에 배치한다. `MainFragment.onCreateView`는 `MainViewModel`을 얻고 `main_fragment` 레이아웃을 반환한다. 현재 `MainViewModel`은 빈 `ViewModel`이어서 저장소·네트워크·DB로 나가는 데이터 흐름은 없다. 공통 화면 기반 클래스는 ViewModel이 `Cleaner`를 구현하면 view 종료 시 `onDestroyView()`를 호출하고, Rx 기반 ViewModel은 `CompositeDisposable`을 정리한다.

### 의존

- 내부: `MainActivity` → `ui/main/MainFragment`; 바인딩 기반 클래스 → `BaseViewModel`·`Cleaner`; 목록 ViewPager → `AutoBindViewPagerAdapter`.
- 외부: AndroidX AppCompat·Fragment·Lifecycle·Navigation·RecyclerView·ViewPager2, Material Components, Kotlin Coroutines, RxJava 3, Hilt, ConstraintLayout, Timber가 `app/build.gradle`에 선언돼 있다.
- 외부 서비스 연동 범주는 `measure.py`의 manifest 탐지 결과 0개다. 외부 클라이언트 생성 지점은 Kotlin용 지정 패턴이 없어 `(미확인 — 패턴 없음)`이다.

### 근거

- M1 익스포트된 최상위 심볼: 지정 1차 `public $T $N($$$) { $$$ }` 0건, 지정 2차 `fun $N($$$) { $$$ }` 3건. 2차 매치는 `InfiniteViewPager2.kt`의 `setAdapter`, `startAutoSlide`, `registerOnPageChangeCallback`이며 테스트 경로는 제외했다 (`ast_grep_helper.py validate/search`, 2026-09-19 실행).
- M2 진입점: Android 화면 패턴 `class $N : AppCompatActivity() { $$$ }` 1건이며 `MainActivity`다. HTTP·CLI 진입점은 해당 없다 (`ast_grep_helper.py validate/search`, 2026-09-19 실행).
- M3 외부 클라이언트 생성 지점: `(미확인 — AST_PATTERNS.md에 Kotlin 패턴 없음)` (2026-09-19 확인).
- M4 이 영역을 부르는 다른 영역: 0개. `app/**`를 제외하고 고정 import 경로 검색 후 최상위 디렉터리를 중복 제거했다 (`rg` 고정 패턴, 2026-09-19 실행).
- 파일·줄 측정: `measure.py`가 추적 파일 64개 중 `app`의 코드 파일 27개·1,268줄을 계산했고, 제외한 심볼릭 링크는 0개다 (2026-09-19 실행).
- 구조·흐름: `app/src/main/AndroidManifest.xml`, `MainActivity.kt`, `ui/main/MainFragment.kt`, `ui/main/MainViewModel.kt`, `base/architecture/`, `base/ui/list/`, `app/build.gradle` (2026-09-19 읽음).

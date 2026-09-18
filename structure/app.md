# app 영역

## 경계

`app/`은 Android 애플리케이션의 manifest, Kotlin 코드, 리소스, 테스트와 모듈 빌드 설정을 소유한다. 루트 Gradle 플러그인·저장소·공통 버전과 Wrapper 배포 설정은 소유하지 않는다.

## 구성

- 전체: 코드 파일 27개, 물리적 코드 1,268줄 (`measure.py`, 2026-09-19 실행)
- `app/src/main/java/com/thingsflow/internapplication/`: `MainActivity`와 앱 패키지 루트, Kotlin 파일 1개.
- `app/src/main/java/com/thingsflow/internapplication/ui/main/`: 주 화면 Fragment와 ViewModel, Kotlin 파일 2개.
- `app/src/main/java/com/thingsflow/internapplication/base/architecture/`: 이벤트·생명주기 정리·바인딩·리소스 변환 기반 코드, Kotlin 파일 15개.
- `app/src/main/java/com/thingsflow/internapplication/base/ui/list/`: 목록 어댑터·장식·무한 ViewPager2 기반 코드, Kotlin 파일 7개.
- `app/src/main/res/`: 레이아웃 4개, values XML 4개, launcher drawable/mipmap 리소스를 둔다.
- `app/src/test/`, `app/src/androidTest/`: 테스트 Kotlin 파일을 각 1개 둔다.

## 데이터 흐름

Android launcher intent가 `MainActivity.onCreate`로 들어오고, 저장 상태가 없으면 `MainFragment.newInstance()`를 `R.id.container`에 배치한다. `MainFragment.onCreateView`는 `MainViewModel`을 얻고 `main_fragment` 레이아웃을 반환한다. 현재 `MainViewModel`은 빈 `ViewModel`이어서 저장소·네트워크·DB로 나가는 데이터 흐름은 없다. 공통 화면 기반 클래스는 ViewModel이 `Cleaner`를 구현하면 view 종료 시 `onDestroyView()`를 호출하고, Rx 기반 ViewModel은 `CompositeDisposable`을 정리한다.

## 의존

- 내부: `MainActivity` → `ui/main/MainFragment`; 바인딩 기반 클래스 → `BaseViewModel`·`Cleaner`; 목록 ViewPager → `AutoBindViewPagerAdapter`.
- 외부: AndroidX AppCompat·Fragment·Lifecycle·Navigation·RecyclerView·ViewPager2, Material Components, Kotlin Coroutines, RxJava 3, Hilt, ConstraintLayout, Timber가 `app/build.gradle`에 선언돼 있다.
- 외부 서비스 연동 범주는 `measure.py`의 manifest 탐지 결과 0개다. 외부 클라이언트 생성 지점은 Kotlin용 지정 패턴이 없어 `(미확인 — 패턴 없음)`이다.

## 근거

- M1 익스포트된 최상위 심볼: 지정 1차 `public $T $N($$$) { $$$ }` 0건, 지정 2차 `fun $N($$$) { $$$ }` 3건. 2차 매치는 `InfiniteViewPager2.kt`의 `setAdapter`, `startAutoSlide`, `registerOnPageChangeCallback`이며 테스트 경로는 제외했다 (`ast_grep_helper.py validate/search`, 2026-09-19 실행).
- M2 진입점: Android 화면 패턴 `class $N : AppCompatActivity() { $$$ }` 1건이며 `MainActivity`다. HTTP·CLI 진입점은 해당 없다 (`ast_grep_helper.py validate/search`, 2026-09-19 실행).
- M3 외부 클라이언트 생성 지점: `(미확인 — AST_PATTERNS.md에 Kotlin 패턴 없음)` (2026-09-19 확인).
- M4 이 영역을 부르는 다른 영역: 0개. `app/**`를 제외하고 고정 import 경로 검색 후 최상위 디렉터리를 중복 제거했다 (`rg` 고정 패턴, 2026-09-19 실행).
- 파일·줄 측정: `measure.py`가 추적 파일 64개 중 `app`의 코드 파일 27개·1,268줄을 계산했고, 제외한 심볼릭 링크는 0개다 (2026-09-19 실행).
- 구조·흐름: `app/src/main/AndroidManifest.xml`, `MainActivity.kt`, `ui/main/MainFragment.kt`, `ui/main/MainViewModel.kt`, `base/architecture/`, `base/ui/list/`, `app/build.gradle` (2026-09-19 읽음).

# 22-5-team4-android

# 🎟️ Waffle Ticket
interpark 클론 코딩 프로젝트입니다.

## 👨‍💻 **프로젝트 참여자 (Contributors)**  

### **백엔드 (Backend)**
김도현
김무진
주정연

### **안드로이드 (Android)**
박정현
정해찬


### **모바일 티켓 예매 플랫폼**
**Waffle Ticket**은 공연, 콘서트, 영화 등의 티켓을 **빠르고 편리하게 예매할 수 있는 안드로이드 애플리케이션**입니다.  
사용자는 카테고리별로 공연을 탐색하고, 좌석을 선택한 후, 티켓을 구매할 수 있습니다.  
또한 예매 후 공연 리뷰를 작성하고 별점을 남길 수도 있습니다.

## 📸 **와이어프레임 (Wireframe)**
아래는 **Waffle Ticket**의 전체적인 사용자 흐름을 시각화한 와이어프레임입니다.
![Untitled](https://github.com/user-attachments/assets/69154546-2575-4788-809d-367312cb7d98)





## 🚀 **주요 기능 (Key Features)**

### 🎭 1. **홈 화면 (Main Screen)**
- 인기 공연 및 추천 티켓 목록을 제공하여 탐색 지원
- 최신 공연 정보 제공

### 🔍 2. **공연 검색 및 탐색 (Browse & Search)**
- 카테고리별 공연 탐색 (뮤지컬, 콘서트, 연극, 스포츠 등)
- 키워드 검색 기능을 통해 원하는 공연 빠르게 찾기

### 🎟️ 3. **티켓 예매 (Ticket Booking)**
- 공연 일정 선택 후 좌석 선택 (좌석 배치도 제공)
- 좌석 선택 후 예매 진행

### ⭐ 4. **리뷰 및 별점 평가 (Review & Rating)**
- 공연 관람 후 사용자 리뷰 작성 가능
- 공연 만족도를 평가할 수 있도록 **별점 시스템 제공**

### 📅 5. **마이페이지 (My Page)**
- 사용자의 예매 내역 확인 가능
- 프로필 및 계정 정보 관리 기능

### 👨‍💻 6. **관리자 페이지 (Admin Page)**
- admin 권한 공연 추가



## 🏗 **아키텍처 개요**
이 앱은 **MVVM (Model-View-ViewModel) 패턴**을 기반으로 하며, 다음과 같은 주요 기술을 활용하여 개발되었습니다.

- **언어:** Kotlin  
- **UI:** XML 기반  
- **패턴:** MVVM  
- **DI (의존성 주입):** ViewModel Factory 사용
- **네트워크:** Retrofit + OkHttp  
- **페이징:** Paging 3 라이브러리  
- **OAuth 로그인:** Kakao API 사용  


## 📂 **폴더 구조**

```plaintext
📂 app
 ├── GlobalApplication.kt  # 앱 전역 설정
 ├── MainActivity.kt  # 앱의 엔트리 포인트
 ├── adapters  # RecyclerView 및 ViewPager Adapter 모음
 ├── auth  # 인증 관련 로직 (OAuth, JWT 관리)
 ├── data  # 데이터 관리 (API 통신, Repository)
 ├── fragments  # 앱 내 주요 화면 프래그먼트
 ├── ui  # 테마 및 스타일 관련 코드
 └── viewModels  # ViewModel 계층 (UI-비즈니스 로직 분리)

```
---

### 📂 폴더 상세 설명

```plaintext
📂 adapters
 ├── CalendarAdapter.kt  # 캘린더 관련 어댑터
 ├── CategoryAdapter.kt  # 카테고리 목록을 표시
 ├── PerformanceAdapter.kt  # 공연 목록을 표시
 ├── SeatAdapter.kt  # 좌석 선택 화면에서 사용
 ├── ViewPagers
     ├── FragmentStateAdapter.kt  # 프래그먼트 상태 관리용 어댑터
     └── PerformanceDetailStateAdapter.kt  # 공연 상세 정보 페이지의 ViewPager

📂 auth
 ├── AuthManager.kt  # 로그인 및 인증 관리
 ├── TokenRefresher.kt  # 토큰 갱신 관리
 ├── tokenInterceptor.kt  # API 요청 시 인증 토큰을 헤더에 추가
 ├── refreshInterceptor.kt  # 토큰 자동 갱신 처리
 └── tokenRefreshScheduler.kt  # 토큰 갱신 스케줄링

📂 data
 ├── API
 │   ├── ApiClient.kt  # Retrofit API 클라이언트
 │   ├── CookieJar.kt  # 쿠키 관리
 │   ├── RetrofitInstance.kt  # Retrofit 설정
 │   └── kakaoAuthClient.kt  # Kakao OAuth API 관리
 ├── PerformancePagingSource.kt  # 공연 목록 페이징 처리
 ├── PerformanceRepository.kt  # 공연 데이터 관리
 ├── ReviewPagingSource.kt  # 리뷰 목록 페이징 처리
 ├── ReviewRepository.kt  # 리뷰 데이터 관리
 ├── SeatRepository.kt  # 좌석 데이터 관리
 ├── reservation.kt  # 예약 관련 데이터 클래스
 └── types
     ├── Admin.kt  # 관리자 데이터 모델
     ├── Category.kt  # 카테고리 데이터 모델
     ├── Comment.kt  # 댓글 데이터 모델
     ├── Performance.kt  # 공연 데이터 모델
     ├── Reservation.kt  # 예약 데이터 모델
     ├── Review.kt  # 리뷰 데이터 모델
     ├── Seat.kt  # 좌석 데이터 모델
     ├── Video.kt  # 동영상 데이터 모델
     └── loginClass.kt  # 로그인 관련 데이터 모델

📂 fragments
 ├── CategoryPage  # 카테고리 관련 화면
 ├── MyPage  # 마이페이지 및 계정 관련 화면
 ├── PerformanceDetail  # 공연 상세 페이지 및 리뷰 관련 화면
 ├── SearchPage  # 검색 화면
 └── bookFlow  # 예매 과정 (좌석 선택, 결제 등)

📂 viewModels
 ├── PerformanceViewModel.kt  # 공연 목록을 관리
 ├── SeatSelectionViewModel.kt  # 좌석 선택 상태 관리
 ├── ReviewViewModel.kt  # 리뷰 작성 및 목록 조회
 └── ViewModelFactory.kt  # ViewModel을 직접 생성하기 위한 Factory 패턴 적용 

```


---

## 🏗 **아키텍처 흐름**

```plaintext
UI (Activity / Fragment) → ViewModel → Repository → API (Remote)
```


---

## ✅ **추가 고려 사항**
```plaintext
- CI/CD 파이프라인 적용 예정 (GitHub Actions)
- 테스트 코드 적용 (Unit Test, UI Test)
- Jetpack Compose 적용 가능성 고려 중
```

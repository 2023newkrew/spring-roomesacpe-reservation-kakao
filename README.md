## 기능 요구사항
1단계
- 웹 요청 / 응답 처리로 입출력 추가
    - [x] 예약 하기
    - [x] 예약 조회
    - [x] 예약 취소
- 예외 처리
  - [x] 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.

2단계
- [x] 콘솔 애플리케이션에 데이터베이스를 적용한다.
- [x] 웹 애플리케이션에 데이터베이스를 적용한다.

3단계 
- [x] 테마 관리 기능 추가
- [x] 테마를 관리하는 테이블을 추가한다.
- [x] 콘솔 애플리케이션과 웹 애플리케이션의 로직의 중복을 제거한다.

## 프로젝트 구조
- Controller
  - ReservationController
  - ThemeController
- Service
  - ReservationService
  - ThemeService
- Repository
  - ReservationRepository (인터페이스)
  - MemoryReservationRepository (인메모리 리스트)
  - JdbcReservationRepository (H2 DB 연동)
  - ThemeRepository
  - JdbcThemeRepository
- Entity
  - Reservation
  - Theme
- Dto
  - ReservationRequest
  - ReservationResponse
  - ThemeRequest
  - ThemeResponse
- Exception
  - RoomEscapeException
  - RoomEscapeExceptionCode
  
## 테스트
- ReservationControllerTest
- ReservationServiceTest
- ReservationRepositoryTest
- ThemeControllerTest
- ThemeServiceTest
- ThemeRepositoryTest

## API 구조
| URI                | Method | 반환값                      | 비고                                                                                               |
|--------------------|--------|--------------------------|--------------------------------------------------------------------------------------------------|
| /reservations      | POST   | 201 / 400                | Reservation 객체를 인자로 받아 id 생성후 Reservation 객체를 새로 만들어 DB에 저장 후 반환, 이미 해당 시간에 예약이 존재하면 bad request |
| /reservations      | DELETE | 204                      | 예약 테이블 전체 삭제                                                                                     |
| /reservations/{id} | GET    | ReservationResponse /400 | 해당 id의 예약 객체 반환, 없으면 bad reqeust                                                                 |
| /reservations/{id} | DELETE | 204                      | 해당 id의 예약 삭제                                                                                     |
| /themes            | POST   | 201                      | Theme 추가후 /themes/{id}로 이동                                                                       |
| /themes            | GET    | List<ThemeResponse>      | 저장된 테마 리스트 반환                                                                                    |
| /themes/{id}       | GET    | ThemeResponse / 400      | 해당 id를 가진 테마 반환, 없으면 bad request                                                                 |
| /themes/{id}       | DELETE | 204 / 400                | 해당 id의 테마 삭제, 테마가 존재하지 않으면 bad request                                                           |

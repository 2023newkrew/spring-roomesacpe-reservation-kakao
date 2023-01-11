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

## 프로젝트 구조
- Controller
  - ReservationController
- Service
  - ReservationService
- Repository
  - ReservationRepository (인터페이스)
  - MemoryReservationRepository (인메모리 리스트)
  - JdbcReservationRepository (H2 DB 연동)
- Entity
  - Reservation
  - Theme
- Exception
  - CreateReservationException

## 테스트
- ReservationControllerTest
- ReservationRepositoryTest

## API 구조
| URI                | Method | 반환값         | 비고                                                               |
|--------------------|--------|-------------|------------------------------------------------------------------|
| /reservations      | POST   | Reservation | Reservation 객체를 인자로 받아 id 생성후 Reservation 객체를 새로 만들어 DB에 저장 후 반환 |
| /reservations      | DELETE | 204         | 예약 테이블 전체 삭제                                                     |
| /reservations/{id} | GET    | Reservation | 해당 id의 예약 객체 반환                                                  |
| /reservations/{id} | DELETE | 204         | 해당 id의 예약 삭제                                                     |


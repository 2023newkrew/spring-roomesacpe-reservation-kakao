## Console

콘솔의 입출력을 통해서 방탈출 예약을 진행한다.

- `ConsoleView`
- `ReservationDAO`
- `RoomEscapeApplication`

## ReservationRepository

- `save`
    - 하나의 Reservation 저장
- `findOne`
    - id를 통해 단건의 Reservation 조회
- `deleteOne`
    - id를 통해 단건의 Reservation 삭제
- `existsByDateAndTime`
    - 같은 date, time을 갖는 Reservation의 존재 여부 확인

## ReservationDAO

- `ReservationRepository`를 구현하며 `콘솔 애플리케이션`에 이용된다.
- `JDBCTemplate`을 사용하지 않는다.

## JdbcReservationRepository

- `ReservationRepository`를 구현하며 `웹 애플리케이션`에 이용된다.
- `JDBCTemplate`을 사용한다.

## JdbcReservationRepositoryTest
- `@JdbcTest`를 이용하여 테스트

## MemoryReservationRepository

- `ReservationRepository`를 구현하며 `ReservationServiceTest`에 `대역`으로써 이용된다.
- __HashMap__ 을 사용한 인메모리 저장소를 구현한다.


## ReservationService

`ReservatationRepository`를 주입받아 사용한다.

- `save`
    - 하나의 Reservation 저장
    - 동일한 date, time을 갖는 Reservation은 생성될 수 없다.
- `findOneById`
    - id를 통해 단건의 Reservation 조회
- `deleteOneById`
    - id를 통해 단건의 Reservation 삭제

## ReservationServiceTest

- ReservationService의 기능의 단위테스트
- `대역` Repository인 `MemeoryReservationRepository`를 이용한다.

## ReservationController

- `예약 단건 생성` `POST`
- `예약 단건 조회` `GET`
- `예약 단건 삭제` `DELETE`
## ReservationControllerTest

- `RestAssured`를 통해 `Controller`의 메서드의 기능을 테스트한다.
- `@MockBean` 으로 `ReservationService`를 이용한다.

# 기능 요구사항

## 1단계
- 웹 요청 / 응답 처리로 입출력 추가
  - 예약 하기
  - 예약 조회
  - 예약 취소
- 예외 처리
  - 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.

### 구현 리스트업
- 예약 생성 API
  - [x] `/reservations`에 post mapping
  - [x] 바디 json을 Reservation 객체로 변환
  - [x] id 추가 후 메모리에 저장
    - [x] 날짜와 시간이 같은 예약이 있는 경우 생성 실패
  - [x] 201 리스폰스

- 예약 조회 API
  - [x] `/reservations/{id}`에 get mapping
  - [x] id가 일치하는 Reservation을 리스폰스에 담기
  - [x] 200 리스폰스

- 예약 삭제 API
  - [x] `reservations/{id}`에 delete mapping
  - [x] id가 일치하는 Reservation을 제거
  - [x] 204 리스폰스

---

## 2단계
- 콘솔 애플리케이션에 데이터베이스를 적용한다.
  - 직접 커넥션을 만들어서 데이터베이스에 접근한다.
- 웹 애플리케이션에 데이터베이스를 적용한다.
  - 스프링이 제공하는 기능을 활용하여 데이터베이스에 접근한다.

### 구현 리스트업
- 콘솔용 DAO : 직접 커넥션 생성해서 reservation 데이터에 접근
  - [x] 예약 추가
  - [x] 예약 조회
  - [x] 예약 삭제

- 웹앱용 DAO : JdbcTemplate를 사용해서 reservation 데이터에 접근
  - [x] 예약 추가
  - [x] 예약 조회
  - [x] 예약 삭제

---

### 리팩토링 목록
- 리뷰 반영
  - [x] 테스트시 테이블 재생성하는 부분을 프로덕션 코드에서 제거, Sql 어노테이션으로 대체
  - [x] WebAppReservationService에서 예약 중복 비교시 변수를 통해 가독성 향상
  - [x] WebAppReservationRepository에서 예외 catch시 로그 출력
  - [x] 필드 주입을 생성자 주입으로 변경
  - [x] Reservation insert 실패시 예외로 처리하도록 수정
  - [x] 자원을 해제하는 코드를 finally 블록 안으로 이동
  - [x] 테스트 이름을 목적과 예상 결과를 포함하도록 수정
  - [x] equals() 오버라이딩 시 hashcode() 함께 오버라이딩
  - [x] 응답 header에서 id를 추출해서 테스트 결과값 지정


- [x] 콘솔 앱과 웹 앱이 같은 Service를 공유하도록 리팩토링
- [x] Reservation 생성메서드 추가
- [x] 콘솔 앱 main 함수 메서드 분리
- [x] ReservationService Test 추가
- [ ] Repository Test 단위 테스트 적용
- [x] ConsoleReservationRepository의 커넥션 획득 예외 처리
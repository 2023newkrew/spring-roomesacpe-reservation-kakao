# 방탈출 예약 관리

---

### 기능 요구사항 - 1단계

- 웹 요청 / 응답 처리로 입출력 추가
  - 예약 하기
  - 예약 조회
  - 예약 취소
- 예외 처리
  - 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약 생성 불가
- 기존 콘솔 어플리케이션은 그대로 잘 동작해야함

---

### 기능 요구사항 - 2단계

- 콘솔 어플리케이션에 데이터베이스를 적용한다
  - 직접 커넥션을 만들어서 데이터베이스에 접근한다
  - JdbcTemplate를 사용하지 않는다.
- 웹 어플리케이션에 데이터베이스를 적용한다
  - JdbcTemplate를 사용하여 데이터베이스에 접근한다.
- H2 데이터베이스를 사용한다.
  - 콘솔 어플리케이션에서는 직접 쿼리를 수행시켜 테이블을 생성한다.
  - 웹 어플리케이션에서는 직접 쿼리를 수행시키지 않고 resources/schema.sql을 추가한다.

---

### API 설계

- 예약 생성

```
POST /reservations HTTP/1.1
content-type: application/json; charset=UTF-8
host: localhost:8080

{
    "date": "2022-08-11",
    "time": "13:00",
    "name": "name"
}
```
```
HTTP/1.1 201 Created
Location: /reservations/1
```

- 예약 조회
```
GET /reservations/{id} HTTP/1.1
```
```
HTTP/1.1 200 
Content-Type: application/json

{
    "id": {id},
    "date": "2022-08-11",
    "time": "13:00",
    "name": "name",
    "themeName": "워너고홈",
    "themeDesc": "병맛 어드벤처 회사 코믹물",
    "themePrice": 29000
}
```

- 예약 삭제
```
DELETE /reservations/{id} HTTP/1.1
```
```
HTTP/1.1 204
```
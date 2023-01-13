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

### 기능 요구사항 - 3단계
- 테마 관리 기능 추가
  - 모든 테마의 시간표는 동일함
- 요구사항에 없는 내용이 있다면 직접 요구사항을 추가해서 어플리케이션을 완성
  - 실제 어플리케이션이라고 생각했을 때 발생할 수 있는 예외 상황을 고려하고 처리
- 테마를 관리하는 테이블을 추가
- 콘솔 어플리케이션과 웹 어플리케이션의 로직의 중복을 제거
  - 디비 접근을 담당하는 객체를 별도로 만들어 사용
  - 비즈니스 로직을 담당하는 객체를 별도로 만들어 사용
- 예약과 관계있는 스케줄 혹은 테마는 수정 및 삭제가 불가능하다.
---

### API 설계

- 예약 생성
  - 날짜, 시간, 이름 정보로 예약을 생성

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
  - 해당하는 id의 예약 내용을 반환
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
  - 해당하는 id의 예약을 삭제하고 예약 내용을 반환
```
DELETE /reservations/{id} HTTP/1.1
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
- 테마 생성
  - 이름, 설명, 가격 정보로 테마를 생성
```
POST /themes HTTP/1.1
content-type: application/json; charset=UTF-8

{
    "name": "테마이름",
    "desc": "테마설명",
    "price": 22000
}
```
```
HTTP/1.1 201 Created
Location: /themes/1
```

- 테마 목록 조회
  - 현재 존재하는 테마의 목록을 조회
```
GET /themes HTTP/1.1
```
```
HTTP/1.1 200 
Content-Type: application/json

[
    {
        "id": 1,
        "name": "테마이름",
        "desc": "테마설명",
        "price": 22000
    }
]
```

- 테마 삭제
  - id에 해당하는 테마를 삭제하고 테마 내용을 반환
```
DELETE /themes/1 HTTP/1.1
```
```
HTTP/1.1 200 
Content-Type: application/json

{
    "id": 1,
    "name": "테마이름",
    "desc": "테마설명",
    "price": 22000
}

```
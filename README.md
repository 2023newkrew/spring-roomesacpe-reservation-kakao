# 기능 요구 사항
- 웹 요청/응답 처리 구현
  - 예약하기
  - 예약 조회
  - 예약 취소
- 예외 처리
  - 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없음

# 프로그래밍 요구사항
- 기존 콘솔 애플리케이션은 그대로 잘 동작해야한다.

# API 설계

## 예약 생성

### 요청
- `POST /reservations`
- JSON message body
  - `date: "yyyy-MM-dd"`
  - `time: "HH-mm"`
  - `name: "name"`

```http request
POST /reservations HTTP/1.1
content-type: application/json; charset=UTF-8
host: localhost:8080

{
    "date": "2022-08-11",
    "time": "13:00",
    "name": "name"
}
```

### 응답
- 성공
  - `201 Created`
  - `Location: /reservations/{생성된 예약 id}`
- 실패 - 날짜와 시간이 정확히 일치하는 예약이 이미 존재하는 경우
  - `400 Bad Request`

```http request
HTTP/1.1 201 Created
Location: /reservations/1
```

## 예약 조회

### 요청
- `GET /reservations/{조회할 예약 id}`
```http request
GET /reservations/1 HTTP/1.1
```

### 응답
- 성공
  - `200 Created`
  - `Content-Type: application/json`
  - `JSON으로 파싱된 Reservation 객체`
- 실패 - id에 해당하는 예약이 존재하지 않는 경우
  - `404 Not Found`

```http request
HTTP/1.1 200 
Content-Type: application/json

{
    "id": 1,
    "date": "2022-08-11",
    "time": "13:00",
    "name": "name",
    "themeName": "워너고홈",
    "themeDesc": "병맛 어드벤처 회사 코믹물",
    "themePrice": 29000
}
```

## 예약 삭제

### 요청
- `DELETE /reservations/{취소할 예약 id}`

```http request
DELETE /reservations/1 HTTP/1.1
```

### 응답
- 성공
  - `204 No Content`
- 실패
  - `404 Not Found`

```http request
HTTP/1.1 204 
```
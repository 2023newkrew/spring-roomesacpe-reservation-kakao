## 상황 설명

### 1단계
- 기존에는 로컬 환경에서 콘솔 애플리케이션을 이용하여 예약 정보를 관리해왔다.
- 콘솔 애플리케이션을 웹 애플리케이션으로 전환하여 집에서도 웹을 통해 예약 관리를 할 수 있도록 해야한다.

### 2단계
- 기존에는 별도의 저장공간을 활용하지 않아 애플리케이션을 종료할 경우 데이터가 사라졌다.
- 데이터베이스를 연동하여 애플리케이션 재부팅 하더라도 데이터가 남아있도록 한다.

## 프로그래밍 요구사항

### 1단계
- 기존 콘솔 애플리케이션은 그대로 잘 동작해야한다.
- 콘솔 애플리케이션과 웹 애플리케이션의 중복 코드는 허용한다. (다음 단계에서 리팩터링 예정)

### 2단계
- 콘솔 애플리케이션과 웹 애플리케이션에서 각각 데이터베이스에 접근하는 로직을 구현한다.
- 콘솔 애플리케이션에서 데이터베이스에 접근 시 `JdbcTemplate`를 사용하지 않는다. 직접 `Connection`을 생성하여 데이터베이스에 접근한다.
- 웹 애플리케이션에서 데이터베이스 접근 시 `JdbcTemplate`를 사용한다.
- 콘솔 애플리케이션과 웹 애플리케이션의 중복 코드 제거해본다.
## API 설계

### 예약 생성
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
```http request
HTTP/1.1 201 Created
Location: /reservations/1
```

### 예약 조회
```http request
GET /reservations/1 HTTP/1.1
```
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

### 예약 삭제
```http request
DELETE /reservations/1 HTTP/1.1
```
```http request
HTTP/1.1 204 
```
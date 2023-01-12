## 상황 설명

### 1단계
- 기존에는 로컬 환경에서 콘솔 애플리케이션을 이용하여 예약 정보를 관리해왔다.
- 콘솔 애플리케이션을 웹 애플리케이션으로 전환하여 집에서도 웹을 통해 예약 관리를 할 수 있도록 해야한다.

### 2단계
- 기존에는 별도의 저장공간을 활용하지 않아 애플리케이션을 종료할 경우 데이터가 사라졌다.
- 데이터베이스를 연동하여 애플리케이션 재부팅 하더라도 데이터가 남아있도록 한다.

### 3단계
- 방탈출 사업이 흥해서 조만간 몇개의 테마가 추가 될 예정이다.
- 테마가 늘어나면서 테마별 정보 관리가 필요해졌다.

## 프로그래밍 요구사항

### 1단계
- 기존 콘솔 애플리케이션은 그대로 잘 동작해야한다.
- 콘솔 애플리케이션과 웹 애플리케이션의 중복 코드는 허용한다. (다음 단계에서 리팩터링 예정)

### 2단계
- 콘솔 애플리케이션과 웹 애플리케이션에서 각각 데이터베이스에 접근하는 로직을 구현한다.
- 콘솔 애플리케이션에서 데이터베이스에 접근 시 `JdbcTemplate`를 사용하지 않는다. 직접 `Connection`을 생성하여 데이터베이스에 접근한다.
- 웹 애플리케이션에서 데이터베이스 접근 시 `JdbcTemplate`를 사용한다.
- 콘솔 애플리케이션과 웹 애플리케이션의 중복 코드 제거해본다.

### 3단계
- 테마를 관리하는 테이블을 추가한다.
- 콘솔 애플리케이션과 웹 애플리케이션의 로직의 중복을 제거한다.
- 디비 접근을 담당하는 객체를 별도로 만들어 사용한다.
- 비즈니스 로직을 담당하는 객체를 별도로 만들어 사용한다.
- 테마 관리 기능 추가
- 모든 테마의 시간표는 동일함
- 요구사항에 없는 내용이 있다면 직접 요구사항을 추가해서 애플리케이션을 완성
- 실제 애플리케이션이라고 생각했을 때 발생할 수 있는 예외 상황을 고려하고 처리한다.

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

### 테마 생성
```http request
POST /themes HTTP/1.1
content-type: application/json; charset=UTF-8

{
    "name": "테마이름",
    "desc": "테마설명",
    "price": 22000
}
```
```http request
HTTP/1.1 201 Created
Location: /themes/1
```

### 테마 목록 조회
```http request
GET /themes HTTP/1.1
```
```http request
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

### 테마 삭제
```http request
DELETE /themes/1 HTTP/1.1
```
```http request
HTTP/1.1 204 
```
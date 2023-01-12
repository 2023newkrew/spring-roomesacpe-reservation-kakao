# 요구사항
- 기존 콘솔 애플리케이션은 그대로 잘 동작해야한다.
# 기능 요구사항
- 웹 요청 / 응답 처리로 입출력 추가
  - 예약 하기
    - 요청
      ```http request
      POST /reservations HTTP/1.1
      Content-Type: application/json; charset=UTF-8
      Host: localhost:8080
        
      {
        "date": "2022-08-11",
        "time": "13:00",
        "name": "name"
      }
      ```
    - 응답
      ```http request
      HTTP/1.1 201 Created
      Location: /reservations/1
      ```
  - 예약 조회
    - 요청
      ```http request
        GET /reservations/1 HTTP/1.1
      ```
    - 응답
      ```
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
  - 예약 취소
    - 요청
      ```
      DELETE /reservations/1 HTTP/1.1
      ```
    - 응답
      ```
      HTTP/1.1 204 
      ```
- 예외 처리
  - 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.
    - 응답
      ```
      HTTP/1.1 400
      ```
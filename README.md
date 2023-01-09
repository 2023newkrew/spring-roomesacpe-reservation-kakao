# Spring Basic - 예약 관리

## 상황 설명

- 기존에는 로컬 환경에서 콘솔 애플리케이션을 이용하여 예약 정보를 관리해왔다.
- 콘솔 애플리케이션을 웹 애플리케이션으로 전환하여 집에서도 웹을 통해 예약 관리를 할 수 있도록 해야한다.

## 요구사항

- [X] 웹 요청 / 응답 처리로 입출력 추가
    - [X] 예약 하기
      ```
      POST /reservations HTTP/1.1
      content-type: application/json; charset=UTF-8
      host: localhost:8080
      
      {
        "date" : "2022-08-11",
        "time" : "12:34:56",
        "name" : "사람 이름"
      }
      ```
      ```
      HTTP/1.1 201 Created
      Location: /reservations/1
      ```
    - [X] 예약 조회
      ```
      GET /reservations/1 HTTP/1.1
      ```
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
    - [X] 예약 취소
      ```
      DELETE /reservations/1 HTTP/1.1
      ```
      ```
      HTTP/1.1 204 
      ```
- [ ] 예외 처리
    - [ ] 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.

## 프로그래밍 요구사항

- 기존 콘솔 애플리케이션은 그대로 잘 동작해야한다.
- 콘솔 애플리케이션과 웹 애플리케이션의 중복 코드는 허용한다. (다음 단계에서 리팩터링 예정)

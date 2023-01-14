## 예약 API Document
### 예약을 관리하는 기능

---

**GET: /reservations/{reservationId}**
- 등록된 예약 중 Id에 대응되는 예약을 반환한다
- **요청**
  ```
  body 없음
  ```
- **응답**
  - Status Code: 200
  ```
  {
    "id": 1,
    "date": "2022-08-11",
    "time": "13:00",
    "name": "예약자",
    "theme": {
      "id": 1,
      "name": "테마이름1",
      "desc": "테마설명1",
      "price": 11000
    }
  }
  ```

**POST: /reservations**
- 새로운 예약을 생성한다
- **요청**
  ```
  {
    "date": "2022-08-11",
    "time": "13:00",
    "name": "name",
    "themeId": 1
  }
  ```
- **응답**
  - Status Code: 201
  ```
  body 없음
  ```

**DELETE: /reservations/{reservationId}**
- 등록된 예약 중 Id에 대응되는 예약을 삭제한다
- **요청**
  ```
  body 없음
  ```
- **응답**
  - Status Code: 204
  ```
  body 없음
  ```

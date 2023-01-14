## 테마 API Document
### 테마를 관리하는 기능

---

**GET: /themes**
- 등록된 테마 전체를 반환한다
- **요청**
    ```
    body 없음
    ```
- **응답**
    - Status Code: 200
    ```
    [
        {
            "id": 1,
            "name": "테마이름1",
            "desc": "테마설명1",
            "price": 11000
        }, 
        {
            "id": 2,
            "name": "테마이름2",
            "desc": "테마설명2",
            "price": 22000
        }, 
    ]
    ```

**GET: /themes/{themeId}**
- 등록된 테마 중 Id에 대응되는 테마를 반환한다
- **요청**
  ```
  body 없음
  ```
- **응답**
  - Status Code: 200
  ```
  {
    "id": 1,
    "name": "테마이름1",
    "desc": "테마설명1",
    "price": 11000
  }
  ```

**POST: /themes**
- 새로운 테마를 생성한다
- **요청**
  ```
  {
    "name": "테마이름",
    "desc": "테마설명",
    "price": 10000
  }
  ```
- **응답**
  - Status Code: 201
  ```
  body 없음
  ```

**PUT: /themes/{themeId}**
- 등록된 테마 중 Id에 대응되는 테마를 수정한다
- **요청**
  ```
  {
    "name": "바꿀 테마이름",
    "desc": "바꿀 테마설명",
    "price": 50000
  }
  ```
- **응답**
  - Status Code: 200
  ```
  body 없음
  ```

**DELETE: /themes/{themeId}**
- 등록된 테마 중 Id에 대응되는 테마를 수정한다
- **요청**
  ```
  body 없음
  ```
- **응답**
  - Status Code: 204
  ```
  body 없음
  ```

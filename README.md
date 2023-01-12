# spring-roomes<u><b>ac</b></u>pe-reservation

# 웹 요청 / 응답 처리로 입출력 추가
## controller
### 예약 하기
* `post`
* `body`로 `Reservation` 정보 받음
* 성공적으로 처리 시 `created()`, `/reservations/{id}`를 반환해야 함

### 예약 조회
* `GET /reservation/{id}`
* 성공적으로 처리 시 `ok()`, `body`에 `Reservation 정보` 반환.

### 예약 취소
* `DELETE /reservation/{id}`
* 성공적으로 처리 시 `noResponse(204)` 반환.

## service
* `controller`에서 받은 데이터를 `repository`에 그대로 전달

## repository
### 예약 하기
* 날짜와 시간이 같은 예약이 없는 경우 DB에 새로 예약을 생성한다.
* 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.

### 예약 조회
* `id`에 해당하는 `reservation` 반환.
* `id`에 해당하는 예약이 없는 경우 예외 처리

### 예약 취소
* `id`에 해당하는 `reservation` 삭제.
* id에 해당하는 예약이 없는 경우 예외 처리


## 3단계 추가 요구 사항
* 테마를 관리하는 테이블을 추가한다.(theme)
* 콘솔 애플리케이션과 웹 애플리케이션의 로직의 중복을 제거한다.
  * 디비 접근을 담당하는 객체를 별도로 만들어 사용한다.
  * 비즈니스 로직을 담당하는 객체를 별도로 만들어 사용한다.

### 3단계 기능 목록
- [ ] post 명령에 대해 테마를 추가한다.
- [ ] get 명령에 대해 테마 정보를 반환한다.
  - [ ] "/themes" 경로의 경우 모든 테마 리스트를 반환한다.
  - [ ] "/themes/{id}"의 경우 id에 해당되는 theme을 반환한다. (추가한 기능)
- [ ] delete 명령에 대해 id에 해당되는 theme을 제거한다.


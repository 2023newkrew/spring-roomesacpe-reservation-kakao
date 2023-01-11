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
* `ReservationDAO`에서 `Reservation`을 받아 `DB` 혹은 `map(임시)`에 추가
* 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.

### 예약 조회
* `id`에 해당하는 `reservation` 반환.
* `id`에 해당하는 예약이 없는 경우 예외 처리

### 예약 취소
* `id`에 해당하는 `reservation` 삭제.
* id에 해당하는 예약이 없는 경우 예외 처리

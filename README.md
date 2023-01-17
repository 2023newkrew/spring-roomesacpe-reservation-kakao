# spring-roomes<u><b>ac</b></u>pe-reservation

# Reservation
## controller
### 예약 하기
* `POST /reservations`
* `body`로 `Reservation` 정보 받음
* 성공적으로 처리 시 `created()`, `/reservations/{id}`를 반환해야 함

### 예약 조회
* `GET /reservations/{id}`
* 성공적으로 처리 시 `ok()`, `body`에 `Reservation 정보` 반환.

### 예약 취소
* `DELETE /reservations/{id}`
* 성공적으로 처리 시 `noResponse(204)` 반환.

## service
* `controller`에서 받은 DTO 데이터를 Entity로 변환해 `repository`에 전달, 혹은 그 반대 역할을 함
* `예약 하기`의 경우 입력값에 대한 검증을 수행(`Theme id`값이 유효한지 등).

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




# Theme
## controller
### 테마 추가
* `POST /themes`
* `body`로 테마에 대한 정보 넘겨줌
* 성공적으로 수행 시 `created`와 헤더에 `Location : /themes/{id}`를 반환해야 함
* 중복된 이름의 테마 추가 시 예외 발생

### 테마 조회
* `GET /themes`
* 성공적으로 수행 시 `ok`와 `body`에 모든 `theme`에 대한 정보를 반환해야 함

### 테마 삭제
* `DELETE /themes/{id}`
* 수행 시 `No content` 반환.

## service
* `controller`에서 받은 DTO 데이터를 Entity로 변환해 `repository`에 전달하거나, 그 반대 역할을 함.

## repository
### 테마 추가
* 받은 Entity를 토대로 데이터베이스에 저장
* 성공적으로 수행 시 DB에서 자동으로 지정된 id 값을 받아와 반환

### 테마 조회
* DB에 있는 모든 테마를 받아와서 List<Entity> 형식으로 반환.

### 테마 삭제
* id를 받아 id가 일치하는 데이터를 삭제하는 쿼리를 보냄
* 이 때, `RESERVATION`에서 참조하는 `theme`는 삭제하지 못함(삭제 시도 시 예외)

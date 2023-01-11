# 방탈출 예약 관리

## 기능 명세

### ReservationController

- [x] Reservation을 생성을 성공하면 201 반환
- [x] 유효하지 않은 Reservation 생성은 400 반환
- [x] 시간대가 겹치는 예약을 하게되면 409 반환
- [x] Reservation 조회를 성공하면 200 반환
- [x] 없는 Reservation 조회 시 404 반환
- [x] Reservation 취소를 성공하면 204 반환
- [x] 없는 예약을 취소하면 404를 반환
    
## 추가적으로 진행할 수정사항

- [ ] Custom Exception 정의했는데 Controller 응답은 No body -> message를 담아서 리턴하도록 해야 함
- [ ] 디비 접근과 비즈니스 로직을 담당하는 객체를 별도로 만들어 계층 분리하기
- [ ] 테스트 시 순서에 상관없이 성공하도록 변경해야 함

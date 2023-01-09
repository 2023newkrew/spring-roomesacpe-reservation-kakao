# spring-roomescape-reservation

## 기능 요구사항
- 웹 요청 / 응답 처리로 입출력 추가
- [x] 예약 하기
- [x] 예약 조회
  - 등록 번호를 기반으로 조회할 수 있다.
- [ ] 예약 취소
  - 등록 번호를 기반으로 취소할 수 있다.
- [ ] 예외 처리
  - [x] 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.
  - [ ] 예약 시 날짜, 시간, 이름이 모두 기재되지 않으면 예약을 생성할 수 없다.
  - [x] id에 해당하는 예약이 없을 경우 예외가 반환된다.

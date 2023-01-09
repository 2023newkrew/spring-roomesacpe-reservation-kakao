# 요구사항

## 기능 요구사항

- [ ] 웹 요청 / 응답 처리로 입출력 추가
    - [ ] 예약 하기
    - [ ] 예약 조회
    - [ ] 예약 취소
- [ ] 예외 처리
    - [ ] 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.
    - [ ] 예약 생성 시 날짜 형식이 'YYYY-MM-DD'이 아닐 경우 예약을 생성할 수 없다.
    - [ ] 예약 생성 시 날짜의 값이 유효하지 않을 경우 예약을 생성할 수 없다.
    - [ ] 예약 생성 시 날짜는 오늘 이전일 경우 예약을 생성할 수 없다.
    - [ ] 예약 생성 시 시간 형식이 'HH:MM'이 아닐 경우 예약을 생성할 수 없다.
    - [ ] 예약 생성 시 시간의 값이 유효하지 않을 경우 예약을 생성할 수 없다.
    - [ ] 예약 생성 시 시간이 30분 간격이 아닐 경우 에약을 생성할 수 없다.
    - [ ] 예약 생성 시 시간이 영업 시간이 아닐 경우 예약을 생성할 수 없다. (예약 가능 시간: 11:00 - 20:30)
    - [ ] 예약 생성 시 이름은 1글자 이상 20글자 이하가 아닐 경우 예약을 생성할 수 없다.
    - [ ] 예약 조회 시 예약이 없을 경우 상태 코드 404를 응답한다.
    - [ ] 예약 삭제 시 예약이 없을 경우 상태 코드 404를 응답한다.

## 프로그래밍 요구사항

- 기존 콘솔 애플리케이션은 그대로 잘 동작해야한다.
- 콘솔 애플리케이션과 웹 애플리케이션의 중복 코드는 허용한다. (다음 단계에서 리팩터링 예정)

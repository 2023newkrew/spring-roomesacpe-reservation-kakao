# 기능 요구사항

## 1단계
- 웹 요청 / 응답 처리로 입출력 추가
  - 예약 하기
  - 예약 조회
  - 예약 취소
- 예외 처리
  - 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.

### 구현 리스트업
- 예약 생성 API
  - [x] `/reservations`에 post mapping
  - [x] 바디 json을 Reservation 객체로 변환
  - [x] id 추가 후 메모리에 저장
    - [x] 날짜와 시간이 같은 예약이 있는 경우 생성 실패
  - [x] 201 리스폰스

- 예약 조회 API
  - [x] `/reservations/{id}`에 get mapping
  - [x] id가 일치하는 Reservation을 리스폰스에 담기
  - [x] 200 리스폰스

- 예약 삭제 API
  - [x] `reservations/{id}`에 delete mapping
  - [x] id가 일치하는 Reservation을 제거
  - [x] 204 리스폰스

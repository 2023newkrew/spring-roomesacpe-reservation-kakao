### 기능 요구사항
- [x] 웹 요청 / 응답 처리로 입출력 추가
  - [x] 예약 하기
  - [x] 예약 조회
  - [x] 예약 취소
- [x] 예외 처리
  - [x] 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.

### 기능 구현 목록
- [x] Spring 프로젝트 구축
- [x] 컨트롤러
  - [x] 예약 api
    - [x] 날짜와 시간이 동일한 경우 예외 처리
  - [x] 조회 api
  - [x] 취소 api

### 추가 기능 요구사항
- [x] 테마 관리
  - [x] 테마 생성
  - [x] 테마 조회
  - [x] 테마 목록 조회
  - [x] 테마 수정
  - [x] 테마 삭제
  - [x] 예외 처리
    - [x] 동일한 이름의 테마 생성 불가
    - [x] 조회/수정/삭제하려는 리소스가 존재하지 않을 경우 예외 메시지 전달
    - [x] 테마 수정 시 이미 존재하는 이름으로 수정 불가
    - [x] 예약이 잡혀있는 테마에 대한 수정 및 삭제 불가능
- [x] 테마 추가로 인한 예약 관리 수정
  - [x] 예약하기


## 기능 요구사항

- 테마 관리시 필요한 생성, 조회, 목록조회, 수정, 삭제 기능을 구현한다.
- 테마는 이름, 설명, 가격을 가지고 있다.
- 테마에 대한 스펙이 변경되었기 때문에 기존 기능인 예약 관리 기능의 로직 수정이 필요할 수 있다.
- 예약과 관계있는 스케줄 혹은 테마는 수정 및 삭제가 불가능하다.

# 제약사항

- 같은 이름의 테마는 존재할 수 없다.

## 기능 목록

* 테마 테이블 추가
* 테마 생성
    - 이름 중복 확인
* 테마 조회
* 테마 목록 조회
* 테마 수정
* 테마 삭제
* 예약 Repository 수정
* 예약 중복 확인 수정
    - 테마까지 동일한지 확인
* 예약 추가 수정
    - 테마 id를 받도록 수정
* 예약 확인 수정
    - 테마 정보까지 join
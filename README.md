# step1, 2
## 기능 요구사항
- [ ] 웹 요청 / 응답 처리로 입출력 추가
    - [ ] 예약 하기
    - [ ] 예약 조회
    - [ ] 예약 취소
- [ ] 예외 처리
- [ ] 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.

## 프로젝트 구조
- Controller
  - ReservationController
- Service
  - ReservationService
- Repository
  - ReservationRepository
- Entity
  - Reservation
  - Theme
- Util
- Request
- Response (Reservation + Theme 합친 정보)

# step 3
## 요구사항
- [x] 테마를 관리하는 테이블을 추가한다.
- [x] 콘솔 애플리케이션과 웹 애플리케이션의 로직의 중복을 제거한다.
  - [x] 디비 접근을 담당하는 객체를 별도로 만들어 사용한다.
  - [x] 비즈니스 로직을 담당하는 객체를 별도로 만들어 사용한다.

## 기존 기능 수정
ReservationService 에서 예약을 생성 할 시 해당하는 Theme가 있는 지 조회하도록 하였습니다.
ThemeService 에서 `예약과 관계있는 스케줄 혹은 테마는 수정 및 삭제가 불가능하다.` 라는 요구사항으로 인해 삭제 전에 예약에 해당 테마 아이디가 존재하는지 확인하도록 하였습니다.

```mysql
CREATE TABLE RESERVATION
(
    id          bigint not null auto_increment,
    date     date,
    time     time,
    name        varchar(20),
    theme_id bigint not null,
    primary key (id)
);

CREATE TABLE theme
(
    id    bigint not null auto_increment,
    name  varchar(20),
    desc  varchar(255),
    price int,
    primary key (id)
);
```
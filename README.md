# 방탈출 프로젝트

### 1단계 요구사항

* 웹 요청 / 응답 처리로 입출력 추가
* 예약 하기
  * 예약 조회
  * 예약 취소
* 예외 처리
  * 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.
---------------------------------------------------------------
### 2단계 요구사항
* 콘솔 애플리케이션에 데이터베이스를 적용한다.
  * 직접 커넥션을 만들어서 데이터베이스에 접근한다.
* 웹 애플리케이션에 데이터베이스를 적용한다.
  * 스프링이 제공하는 기능을 활용하여 데이터베이스에 접근한다.
---------------------------------------------------------------
### 3단계 요구사항
* 테마 관리 기능 추가
  * 모든 테마의 시간표는 동일함
* 요구사항에 없는 내용이 있다면 직접 요구사항을 추가해서 애플리케이션을 완성
  * 실제 애플리케이션이라고 생각했을 때 발생할 수 있는 예외 상황을 고려하고 처리한다.
---------------------------------------------------------------
### 사용 방법
* Java Main (RoomEscapeApplication) 실행
  * API 명세에 있는 Rest API 사용
  * Console(ConsoleApplication)을 이용
* http://localhost:8080/h2-console 에서 DB 조회 가능


### API

* 예약생성
![reserve_create.png](reserve_create.png)
* 예약조회
![reserve_search.png](reserve_search.png)
* 예약삭제
![reserve_delete.png](reserve_delete.png)


### Test Case
* Web
  * 방탈출 예약이 가능함
  * 방탈출 예약이 되었다면 조회할 수 있음
  * 방탈출 예약이 되었다면 취소할 수 있음
  * 동일한 날짜/시간대에 예약을 하는 경우, 예외가 발생
  * 등록되지 않은 ID를 조회할 경우, 예외가 발생
* Console
  * 방탈출 예약이 가능하고 조회할 수 있음
  * 방탈출 예약이 되었다면 취소할 수 있음
  * 동일한 날짜/시간대에 예약을 하는 경우, 예외가 발생
  * 등록되지 않은 ID를 조회할 경우, 예외가 발생
* Theme
  * 테마 등록이 가능함
  * 테마가 등록되었다면 조회할 수 있음
  * 테마가 등록되었다면 취소할 수 있음
  * 동일한 이름/가격의 테마가 등록된 경우, 예외가 발생
  * 등록되지 않은 ID를 조회할 경우, 예외가 발생
  * 예약이 존재하는 테마는 삭제할 수 없음


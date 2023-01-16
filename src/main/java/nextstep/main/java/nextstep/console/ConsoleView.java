package nextstep.main.java.nextstep.console;

import nextstep.main.java.nextstep.mvc.domain.reservation.Reservation;

@Deprecated
public class ConsoleView {
    public void printCommand() {
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
        System.out.println("- 종료: quit");
    }

    public void printRegisteredReservationInfo(Reservation reservation) {
        System.out.println("예약이 등록되었습니다.");
        System.out.println("예약 번호: " + reservation.getId());
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
    }

    public void printReservationInfo(Reservation reservation) {
        System.out.println("예약 번호: " + reservation.getId());
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
        System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
        System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
        System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
    }

    public void printCancelReservation() {
        System.out.println("예약이 취소되었습니다.");
    }

}

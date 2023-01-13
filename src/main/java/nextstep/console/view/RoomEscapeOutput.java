package nextstep.console.view;

import nextstep.reservation.dto.ReservationResponse;
import nextstep.reservation.dto.ThemeResponse;

import java.util.Objects;

public class RoomEscapeOutput {

    public void printMenu() {
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
        System.out.println("- 종료: quit");
    }

    public void printAddReservation(ReservationResponse reservation) {
        System.out.println("예약이 등록되었습니다.");
        printSimpleReservationInfo(reservation);
    }

    private void printSimpleReservationInfo(ReservationResponse reservation) {
        System.out.println("예약 번호: " + reservation.getId());
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
    }

    public void printFindReservation(ReservationResponse reservation) {
        if (Objects.isNull(reservation)) {
            System.out.println("예약이 없습니다.");
            return;
        }
        printSimpleReservationInfo(reservation);
        printTheme(reservation.getTheme());
    }

    private void printTheme(ThemeResponse theme) {
        System.out.println("예약 테마 이름: " + theme.getName());
        System.out.println("예약 테마 설명: " + theme.getDesc());
        System.out.println("예약 테마 가격: " + theme.getPrice());
    }

    public void printDeleteReservation() {
        System.out.println("예약이 취소되었습니다.");
    }

    public void printError(String message) {
        System.out.println(message);
    }
}

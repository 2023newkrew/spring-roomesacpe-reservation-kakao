package nextstep.console.view;

import nextstep.dto.FindReservationResponse;

import java.util.Scanner;

public class ConsoleReservationView {
    private final Scanner sc;

    public ConsoleReservationView() {
        this.sc = new Scanner(System.in);
    }

    public void printCommand() {
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: add {date},{time},{name},{themeId} ex) add 2022-08-11,13:00,류성현,1");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
        System.out.println("- 뒤로가기: back");
    }

    public String inputCommand() {
        return sc.nextLine();
    }

    public void printAddMessage() {
        System.out.println("예약이 등록되었습니다.");
    }

    public void printDeleteMessage() {
        System.out.println("예약이 취소되었습니다.");
    }

    public void printReservation(FindReservationResponse reservation) {
        System.out.println("예약 번호: " + reservation.getReservationId());
        System.out.println("예약 날짜: " + reservation.getReservationDate());
        System.out.println("예약 시간: " + reservation.getReservationTime());
        System.out.println("예약자 이름: " + reservation.getReservationName());
        System.out.println("예약 테마 번호: " + reservation.getThemeId());
        System.out.println("예약 테마 이름: " + reservation.getThemeName());
        System.out.println("예약 테마 설명: " + reservation.getThemeDesc());
        System.out.println("예약 테마 가격: " + reservation.getThemePrice());
        System.out.println();
    }

}

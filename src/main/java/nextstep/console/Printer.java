package nextstep.console;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

public class Printer {

    public static void printGuideMessage() {
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: 예약 add {date},{time},{name},{themeId} ex) 예약 add 2022-08-11,13:00,류성현,1");
        System.out.println("- 예약조회: 예약 find {id} ex) 예약 find 1");
        System.out.println("- 예약취소: 예약 delete {id} ex) 예약 delete 1");
        System.out.println("- 테마생성: 테마 add {테마이름},{테마설명},{테마가격} ex) 테마 add 귀신의집,무서워요,22000");
        System.out.println("- 테마조회: 테마 find {id} ex) 테마 find 1");
        System.out.println("- 테마수정: 테마 update {테마번호},{테마이름},{테마설명},{테마가격} ex) 테마 update 1,귀신의집,무서워요,22000");
        System.out.println("- 테마취소: 테마 delete {id} ex) 테마 delete 1");
        System.out.println("- 종료: quit");
    }

    public static void printReservationConfirmMessage(Reservation reservation) {
        System.out.println("예약이 등록되었습니다.");
        System.out.println("예약 번호: " + reservation.getId());
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
    }

    public static void printReservationInfo(Reservation reservation) {
        System.out.println("예약 번호: " + reservation.getId());
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
        System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
        System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
        System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
    }

    public static void printThemeConfirmMessage() {
        System.out.println("테마가 등록되었습니다.");
    }

    public static void printThemeInfo(Theme theme) {
        System.out.println("테마 이름: " + theme.getName());
        System.out.println("테마 설명: " + theme.getDesc());
        System.out.println("테마 가격: " + theme.getPrice());
    }

    public static void printReservationCancelMessage() {
        System.out.println("예약이 취소되었습니다.");
    }

    public static void printErrorMessage(Exception e) {
        System.out.println(e.getMessage());
    }

    public static void printInvalidInputErrorMessage() {
        System.out.println("유효하지 않은 입력입니다.");
    }
}

package roomescape.console.view;

import java.util.List;
import roomescape.dto.Reservation;
import roomescape.dto.Theme;

public class ResultView {

    public static void printCreateReservation(long id, Reservation reservation, Theme theme) {
        System.out.println("예약이 등록되었습니다.");
        printReservationInformation(id, reservation, theme);
    }

    public static void printReservationInformation(long id, Reservation reservation, Theme theme) {
        System.out.println("예약 번호: " + id);
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
        System.out.println("예약 테마 번호: " + reservation.getThemeId());
        System.out.println("예약 테마 이름: " + theme.getName());
        System.out.println("예약 테마 설명: " + theme.getDesc());
        System.out.println("예약 테마 가격: " + theme.getPrice());
    }

    public static void printRemoveReservation() {
        System.out.println("예약이 취소되었습니다.");
    }

    public static void printCreateTheme(Theme theme, Long id) {
        System.out.println("테마가 추가되었습니다.");
        printThemeInformation(id, theme);
    }

    public static void printListTheme(List<Theme> themeList) {
        for (Theme theme : themeList) {
            ResultView.printThemeInformation(theme.getId(), theme);
        }
    }

    public static void printThemeInformation(long id, Theme theme) {
        System.out.println("- 테마 번호: " + id);
        System.out.println("- 테마 이름: " + theme.getName());
        System.out.println("- 테마 설명: " + theme.getDesc());
        System.out.println("- 테마 가격: " + theme.getPrice());
    }

    public static void printRemoveTheme() {
        System.out.println("테마가 삭제되었습니다.");
    }
}

package nextstep;

import nextstep.dao.ReservationJdbcApiDAO;
import nextstep.dao.ThemeJdbcApiDAO;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;

import java.util.Scanner;

public class ConsoleRoomEscapeApplication {
    private static final String ADD_RESERVATION = "add_reservation";
    private static final String FIND_RESERVATION = "find_reservation";
    private static final String DELETE_RESERVATION = "delete_reservation";
    private static final String ADD_THEME = "add_theme";
    private static final String ALL_THEME = "all_theme";
    private static final String DELETE_THEME = "delete_theme";
    private static final String QUIT = "quit";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleRoomEscapeManager roomEscapeManager = new ConsoleRoomEscapeManager(
                new ReservationService(new ReservationJdbcApiDAO()),
                new ThemeService(new ThemeJdbcApiDAO())
        );

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add_reservation {date},{time},{name},{theme_id} ex) add_reservation 2022-08-11,13:00,류성현,1");
            System.out.println("- 예약조회: find_reservation {id} ex) find_reservation 1");
            System.out.println("- 예약취소: delete_reservation {id} ex) delete_reservation 1");
            System.out.println("- 테마등록: add_theme {name},{desc},{price} ex) add_theme 테마이름,테마설명,22000");
            System.out.println("- 테마조회: all_theme");
            System.out.println("- 테마삭제: delete_theme {id} ex) delete_theme 1");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(ADD_RESERVATION)) {
                roomEscapeManager.addReservation(input);
            }
            if (input.startsWith(FIND_RESERVATION)) {
                roomEscapeManager.findReservation(input);
            }
            if (input.startsWith(DELETE_RESERVATION)) {
                roomEscapeManager.deleteReservation(input);
            }
            if (input.startsWith(ADD_THEME)) {
                roomEscapeManager.addTheme(input);
            }
            if (input.startsWith(ALL_THEME)) {
                roomEscapeManager.findAllTheme(input);
            }
            if (input.startsWith(DELETE_THEME)) {
                roomEscapeManager.deleteTheme(input);
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

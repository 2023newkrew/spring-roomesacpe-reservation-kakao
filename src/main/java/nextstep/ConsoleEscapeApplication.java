package nextstep;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exception.ReservationException;
import nextstep.repository.reservation.ConsoleReservationRepository;
import nextstep.repository.theme.ConsoleThemeRepository;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class ConsoleEscapeApplication {

    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";
    private static final String THEMES = "themes";
    private static final String ADD_THEME = "newT";
    private static final String DELETE_THEME = "delT";

    private static final String RESERVATION_SUCCESS_MESSAGE = "예약이 등록되었습니다.";
    private static final String RESERVATION_CANCEL_SUCCESS_MESSAGE = "예약이 취소되었습니다.";
    private static final String THEME_SUCCESS_MESSAGE = "테마가 등록되었습니다.";
    private static final String THEME_DELETE_SUCCESS_MESSAGE = "테마가 삭제되었습니다.";

    private static final ConsoleReservationRepository consoleReservationRepository
            = new ConsoleReservationRepository();
    private static final ReservationService reservationService =
            new ReservationService(consoleReservationRepository);

    private static final ThemeService themeService =
            new ThemeService(new ConsoleThemeRepository(), consoleReservationRepository);

    public static void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                printHowToUse();

                String input = scanner.nextLine();
                if (input.startsWith(THEMES)) {
                    showThemes();
                }

                if (input.startsWith(ADD_THEME)) {
                    makeTheme(input);
                }

                if (input.startsWith(DELETE_THEME)) {
                    deleteTheme(input);
                }

                if (input.startsWith(ADD)) {
                    makeReservation(input);
                }

                if (input.startsWith(FIND)) {
                    getReservation(input);
                }

                if (input.startsWith(DELETE)) {
                    cancelReservation(input);
                }

                if (input.equals(QUIT)) {
                    break;
                }
            } catch (ReservationException e) {
                System.err.println(e.getErrorMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    private static void deleteTheme(String input) {
        String params = input.split(" ")[1];

        Long id = Long.parseLong(params.split(",")[0]);

        themeService.deleteTheme(id);
        System.out.println(THEME_DELETE_SUCCESS_MESSAGE);
    }

    private static void makeTheme(String input) {
        String params = input.split(" ")[1];

        String name = params.split(",")[0];
        String desc = params.split(",")[1];
        Integer price = Integer.parseInt(params.split(",")[2]);

        Long savedId = themeService.createTheme(name, desc, price);

        Theme savedTheme = themeService.findById(savedId);

        System.out.println(THEME_SUCCESS_MESSAGE);
        printReservationThemeInfo(savedTheme);
    }

    private static void showThemes() {
        List<Theme> themes = themeService.findAll();
        for (Theme theme : themes) {
            System.out.println("테마 번호: " + theme.getId());
            System.out.println("테마 이름: " + theme.getName());
            System.out.println("테마 설명: " + theme.getDesc());
            System.out.println("테마 가격: " + theme.getPrice());
            System.out.println();
        }
    }

    private static void printHowToUse() {
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 테마보기: themes");
        System.out.println("- 테마추가: newT {name},{desc},{price} ex) addT 라이언의 탈옥,라이언이 감옥을 탈출했다,39000");
        System.out.println("- 테마삭제: delT {id} ex) delete 1");
        System.out.println("- 예약하기: add {date},{time},{name},{themeId} ex) add 2022-08-11,13:00,류성현,1");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
        System.out.println("- 종료: quit");
    }

    private static void makeReservation(String input) {
        String params = input.split(" ")[1];

        String date = params.split(",")[0];
        String time = params.split(",")[1];
        String name = params.split(",")[2];
        Long themeId = Long.parseLong(params.split(",")[3]);

        // 입력받은 테마가 존재하는지 확인
        Theme foundTheme = themeService.findById(themeId);
        // 예약 데이터 생성
        Long id = reservationService.createReservation(LocalDate.parse(date),
                LocalTime.parse(time + ":00"), name, foundTheme.getId());

        // 안내 문구 출력
        Reservation reservation = reservationService.findById(id);
        System.out.println(RESERVATION_SUCCESS_MESSAGE);
        printReservationInfo(reservation);
    }

    private static void getReservation(String input) {
        String params = input.split(" ")[1];
        Long id = Long.parseLong(params.split(",")[0]);

        Reservation reservation = reservationService.findById(id);
        Theme theme = themeService.findById(reservation.getThemeId());

        printReservationInfo(reservation);
        printReservationThemeInfo(theme);
    }

    private static void cancelReservation(String input) {
        String params = input.split(" ")[1];

        Long id = Long.parseLong(params.split(",")[0]);

        reservationService.deleteReservation(id);
        System.out.println(RESERVATION_CANCEL_SUCCESS_MESSAGE);
    }

    private static void printReservationInfo(Reservation reservation) {
        System.out.println("예약 번호: " + reservation.getId());
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
    }

    private static void printReservationThemeInfo(Theme theme) {
        System.out.println("예약 테마 이름: " + theme.getName());
        System.out.println("예약 테마 설명: " + theme.getDesc());
        System.out.println("예약 테마 가격: " + theme.getPrice());
    }
}

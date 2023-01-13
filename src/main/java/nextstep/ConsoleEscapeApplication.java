package nextstep;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exception.ReservationException;
import nextstep.repository.reservation.ConsoleReservationRepository;
import nextstep.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class ConsoleEscapeApplication {

    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    private static final String RESERVATION_SUCCESS_MESSAGE = "예약이 등록되었습니다.";
    private static final String RESERVATION_CANCEL_SUCCESS_MESSAGE = "예약이 취소되었습니다.";

    private static final ReservationService reservationService =
            new ReservationService(new ConsoleReservationRepository());

    public static void run() {
        Scanner scanner = new Scanner(System.in);

        // 현재는 테마 커스터마이징 관련 기능이 필요 없으므로 하드코딩
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

        while (true) {
            try {
                printHowToUse();

                String input = scanner.nextLine();
                if (input.startsWith(ADD)) {
                    makeReservation(theme, input);
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

    private static void printHowToUse() {
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
        System.out.println("- 종료: quit");
    }

    private static void makeReservation(Theme theme, String input) {
        String params = input.split(" ")[1];

        String date = params.split(",")[0];
        String time = params.split(",")[1];
        String name = params.split(",")[2];

        Long id = reservationService.createReservation(LocalDate.parse(date),
                LocalTime.parse(time + ":00"), name, theme);
        Reservation reservation = reservationService.findById(id);

        System.out.println(RESERVATION_SUCCESS_MESSAGE);
        printReservationInfo(reservation);
    }

    private static void getReservation(String input) {
        String params = input.split(" ")[1];
        Long id = Long.parseLong(params.split(",")[0]);

        Reservation reservation = reservationService.findById(id);

        printReservationInfo(reservation);
        printReservationThemeInfo(reservation);
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

    private static void printReservationThemeInfo(Reservation reservation) {
        System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
        System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
        System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
    }
}

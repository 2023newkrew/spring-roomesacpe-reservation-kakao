package nextstep.console;

import nextstep.exception.RoomEscapeException;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.repository.ReservationRepository;
import nextstep.service.ReservationService;
import nextstep.dto.ReservationRequest;
import nextstep.service.ThemeService;
import nextstep.web.JdbcTemplateThemeRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ReservationRepository reservationRepository = new JdbcReservationRepository();
        ReservationService reservationService = new ReservationService(reservationRepository);
        JdbcTemplateThemeRepository themeRepository = new JdbcTemplateThemeRepository(new JdbcTemplate());
        ThemeService themeService = new ThemeService(themeRepository);

        while (true) {
            String input = getInput(scanner);
            try {
                if (input.startsWith(ADD)) {
                    ReservationRequest request = parseReservationRequestFromInput(input);
                    // TODO : theme 조회
                    Reservation reservation = reservationService.createReservation(request);
                    printReservation(reservation);
                }
                if (input.startsWith(FIND)) {
                    Long id = parseIdFromInput(input);

                    Reservation reservation = reservationService.getReservation(id);
                    printReservation(reservation);

                    Theme theme = themeService.getTheme(reservation.getThemeId());
                    printReservationTheme(theme);
                }
                if (input.startsWith(DELETE)) {
                    Long id = parseIdFromInput(input);
                    reservationService.deleteReservation(id);
                }
                if (input.equals(QUIT)) {
                    break;
                }
            } catch (RoomEscapeException e) {
                System.err.println(e.getMessage());
            }
        }
        scanner.close();
    }

    public static String getInput(Scanner scanner) {
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
        System.out.println("- 종료: quit");

        return scanner.nextLine();
    }

    public static ReservationRequest parseReservationRequestFromInput(String input) {
        String params = input.split(" ")[1];
        String date = params.split(",")[0];
        String time = params.split(",")[1];
        String name = params.split(",")[2];
        String themeId = params.split(",")[3];

        return new ReservationRequest(name, LocalDate.parse(date), LocalTime.parse(time), Long.parseLong(themeId));
    }

    public static Long parseIdFromInput(String input) {
        String params = input.split(" ")[1];
        return Long.parseLong(params.split(",")[0]);
    }

    public static void printReservation(Reservation reservation) {
        System.out.println("예약 번호: " + reservation.getId());
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
    }

    public static void printReservationTheme(Theme theme) {
        System.out.println("예약 테마 이름: " + theme.getName());
        System.out.println("예약 테마 설명: " + theme.getDesc());
        System.out.println("예약 테마 가격: " + theme.getPrice());
    }
}

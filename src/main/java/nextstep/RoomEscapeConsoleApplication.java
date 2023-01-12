package nextstep;

import nextstep.domain.dto.CreateReservationDto;
import nextstep.domain.dto.GetReservationDto;
import nextstep.domain.theme.Theme;
import nextstep.domain.reservation.Reservation;
import nextstep.exception.DeleteReservationFailureException;
import nextstep.exception.DuplicateTimeReservationException;
import nextstep.exception.NoReservationException;
import nextstep.repository.ConsoleReservationRepository;
import nextstep.service.ReservationService;

import java.util.Scanner;

public class RoomEscapeConsoleApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";
    private static final Theme defaultTheme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private static final ReservationService reservationService = new ReservationService(new ConsoleReservationRepository());
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            String input = getInput();
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];

                insertReservation(date, time, name);
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                showReservationInfo(id);
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                deleteReservation(id);
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }

    private static void deleteReservation(Long id) {
        try {
            reservationService.deleteReservation(id);
        } catch (DeleteReservationFailureException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void showReservationInfo(Long id) {
        try {
            Reservation reservation = reservationService.getReservation(id);
            System.out.println("예약 번호: " + reservation.getId());
            System.out.println("예약 날짜: " + reservation.getDate());
            System.out.println("예약 시간: " + reservation.getTime());
            System.out.println("예약자 이름: " + reservation.getName());
            System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
            System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
            System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
        } catch (NoReservationException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void insertReservation(String date, String time, String name) {
        CreateReservationDto createReservationDto = new CreateReservationDto(date, time, name, defaultTheme);
        try {
            long id = reservationService.addReservation(createReservationDto);
            System.out.println("예약이 등록되었습니다.");
            System.out.println("예약 번호: " + id);
            System.out.println("예약 날짜: " + date);
            System.out.println("예약 시간: " + time);
            System.out.println("예약자 이름: " + name);
        } catch (DuplicateTimeReservationException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static String getInput() {
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
        System.out.println("- 종료: quit");

        String input = scanner.nextLine();
        return input;
    }
}

package nextstep;

import nextstep.dto.console.request.CreateReservationConsoleRequest;
import nextstep.dto.console.response.ReservationConsoleResponse;
import nextstep.exception.DatabaseException;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.repository.reservation.ReservationH2Repository;
import nextstep.repository.reservation.ReservationRepository;
import nextstep.service.ReservationService;
import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservationRepository repository = new ReservationH2Repository();
        ReservationService reservationService = new ReservationService(repository);

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현");
            System.out.println("- 예약조회: find {id} ex) find 1");
            System.out.println("- 예약취소: delete {id} ex) delete 1");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];

                try {
                    ReservationConsoleResponse reservation = reservationService.createReservationForConsole(CreateReservationConsoleRequest.of(date, time, name));
                    System.out.println("예약이 등록되었습니다.");
                    System.out.println("예약 번호: " + reservation.getId());
                    System.out.println("예약 날짜: " + reservation.getDate());
                    System.out.println("예약 시간: " + reservation.getTime());
                    System.out.println("예약자 이름: " + reservation.getName());
                } catch (DuplicateReservationException | DatabaseException e) {
                    System.out.println(e.getMessage());
                }


            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                try {
                    ReservationConsoleResponse reservation = reservationService.findReservationForConsole(id);

                    System.out.println("예약 번호: " + reservation.getId());
                    System.out.println("예약 날짜: " + reservation.getDate());
                    System.out.println("예약 시간: " + reservation.getTime());
                    System.out.println("예약자 이름: " + reservation.getName());
                    System.out.println("예약 테마 이름: " + reservation.getThemeName());
                    System.out.println("예약 테마 설명: " + reservation.getThemeDesc());
                    System.out.println("예약 테마 가격: " + reservation.getThemePrice());
                } catch (ReservationNotFoundException | DatabaseException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                try {
                    reservationService.cancelReservation(id);
                    System.out.println("예약이 취소되었습니다.");
                } catch (DatabaseException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

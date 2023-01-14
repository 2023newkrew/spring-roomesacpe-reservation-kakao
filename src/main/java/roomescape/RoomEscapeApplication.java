package roomescape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.ReservationConsoleRepository;
import roomescape.repository.ThemeConsoleRepository;
import roomescape.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import static roomescape.DataLoader.WANNA_GO_HOME;

@SpringBootApplication
public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    public static void main(String[] args) {
        startSpringBootApplication(args);
        startConsoleApplication();
    }

    private static void startSpringBootApplication(String[] args) {
        SpringApplication.run(RoomEscapeApplication.class, args);
    }

    private static void startConsoleApplication() {
        Scanner scanner = new Scanner(System.in);
        ReservationService reservationService = new ReservationService(
                new ReservationConsoleRepository(),
                new ThemeConsoleRepository()
        );

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

                LocalDate date = LocalDate.parse(params.split(",")[0]);
                LocalTime time = LocalTime.parse(params.split(",")[1] + ":00");
                String name = params.split(",")[2];

                ReservationRequest reservationRequest = new ReservationRequest(
                        date.toString(),
                        time.toString(),
                        name,
                        WANNA_GO_HOME.getId().toString()
                );

                Long reservationId;

                try {
                    reservationId = reservationService.createReservation(reservationRequest);
                } catch (RoomEscapeException e) {
                    System.err.println(e.getMessage());
                    continue;
                }

                Reservation reservation = new Reservation(reservationId, date, time, name, WANNA_GO_HOME);
                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                ReservationResponse reservationResponse;

                try {
                    reservationResponse = reservationService.getReservation(id);
                } catch (RoomEscapeException e) {
                    System.err.println(e.getMessage());
                    continue;
                }

                System.out.println("예약 번호: " + reservationResponse.getId());
                System.out.println("예약 날짜: " + reservationResponse.getDate());
                System.out.println("예약 시간: " + reservationResponse.getTime());
                System.out.println("예약자 이름: " + reservationResponse.getName());
                System.out.println("예약 테마 이름: " + reservationResponse.getTheme().getName());
                System.out.println("예약 테마 설명: " + reservationResponse.getTheme().getDesc());
                System.out.println("예약 테마 가격: " + reservationResponse.getTheme().getPrice());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                try {
                    reservationService.deleteReservation(id);
                    System.out.println("예약이 취소되었습니다.");
                } catch (RoomEscapeException e) {
                    System.err.println(e.getMessage());
                    continue;
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

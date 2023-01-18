package nextstep.roomescape;

import lombok.RequiredArgsConstructor;
import nextstep.roomescape.exception.NotExistEntityException;
import nextstep.roomescape.controller.RequestDTO.ReservationRequestDTO;
import nextstep.roomescape.controller.ResponseDTO.ReservationResponseDTO;
import nextstep.roomescape.service.ReservationService;
import nextstep.roomescape.repository.model.Theme;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ThemeReservationConsoleController {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    private final ReservationService reservationServiceImpl;

    public void play() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

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

                ReservationRequestDTO reservation = new ReservationRequestDTO(
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name,
                        theme
                );
                try {
                    Long createReservationID = reservationServiceImpl.create(reservation);

                    System.out.println("예약이 등록되었습니다.");
                    printReservation(reservationServiceImpl.findById(createReservationID));
                } catch (ClassCastException e) {
                    System.out.println(e.getMessage());
                }

            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);
                ReservationResponseDTO reservation = reservationServiceImpl.findById(id);

                printReservation(reservation);
                printTheme(reservation);
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);
                try {
                    reservationServiceImpl.delete(id);
                    System.out.println("예약이 취소되었습니다.");
                } catch (NotExistEntityException e) {
                    System.out.println(e.getMessage());
                }

            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }

    public static void printReservation(ReservationResponseDTO reservation) {
        System.out.println("예약 번호: " + reservation.getId());
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
    }

    public static void printTheme(ReservationResponseDTO reservation) {
        System.out.println("예약 테마 이름: " + reservation.getThemeName());
        System.out.println("예약 테마 설명: " + reservation.getThemeDesc());
        System.out.println("예약 테마 가격: " + reservation.getThemePrice());
    }
}

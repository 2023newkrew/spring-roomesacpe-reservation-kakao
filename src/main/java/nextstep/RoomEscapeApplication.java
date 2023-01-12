package nextstep;

import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.service.ReservationService;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    public static void main(String[] args) {
        ReservationConsoleRepository reservationConsoleRepository = new ReservationConsoleRepository();
        ThemeConsoleRepository themeConsoleRepository = new ThemeConsoleRepository();
        ReservationService reservationService = new ReservationService(reservationConsoleRepository, themeConsoleRepository);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add {date},{time},{name},{theme_id} ex) add 2022-08-11,13:00,류성현,1");
            System.out.println("- 예약조회: find {id} ex) find 1");
            System.out.println("- 예약취소: delete {id} ex) delete 1");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                LocalDate date = LocalDate.parse(params.split(",")[0]);
                LocalTime time = LocalTime.parse(params.split(",")[1] + ":00");
                String name = params.split(",")[2];
                Long themeId = Long.parseLong(params.split(",")[3]);

                ReservationRequestDto reservationRequestDto = new ReservationRequestDto(date, time, name, themeId);

                Long reservationId = reservationService.createReservation(reservationRequestDto);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservationId);
                System.out.println("예약 날짜: " + reservationRequestDto.getDate());
                System.out.println("예약 시간: " + reservationRequestDto.getTime());
                System.out.println("예약자 이름: " + reservationRequestDto.getName());
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                ReservationResponseDto reservationResponseDto = reservationService.findReservation(id);

                System.out.println("예약 번호: " + reservationResponseDto.getId());
                System.out.println("예약 날짜: " + reservationResponseDto.getDate());
                System.out.println("예약 시간: " + reservationResponseDto.getTime());
                System.out.println("예약자 이름: " + reservationResponseDto.getName());
                System.out.println("예약 테마 이름: " + reservationResponseDto.getThemeName());
                System.out.println("예약 테마 설명: " + reservationResponseDto.getThemeDesc());
                System.out.println("예약 테마 가격: " + reservationResponseDto.getThemePrice());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                reservationService.deleteReservation(id);
                System.out.println("예약이 취소되었습니다.");
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

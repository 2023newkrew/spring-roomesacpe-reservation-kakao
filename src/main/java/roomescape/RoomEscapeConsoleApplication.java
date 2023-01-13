package roomescape;

import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.repository.ReservationRepositoryWithoutTemplateImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import roomescape.reservation.service.ReservationService;
import roomescape.theme.repository.ThemeRepository;
import roomescape.theme.repository.ThemeRepositoryWithoutTemplateImpl;

public class RoomEscapeConsoleApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";
    private static final ReservationRepository reservationRepository = new ReservationRepositoryWithoutTemplateImpl();
    private static final ThemeRepository themeRepository = new ThemeRepositoryWithoutTemplateImpl();
    private static final ReservationService reservationService = new ReservationService(reservationRepository,
            themeRepository);

    public static void main(String[] args) {
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

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];
                String themeId = params.split(",")[3];

                ReservationRequestDto requestDto = ReservationRequestDto.builder()
                        .time(LocalTime.parse(time + ":00"))
                        .date(LocalDate.parse(date))
                        .name(name)
                        .themeId(Long.valueOf(themeId))
                        .build();
                Long reservationId = reservationService.makeReservation(requestDto);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservationId);
                System.out.println("예약 날짜: " + LocalDate.parse(date));
                System.out.println("예약 시간: " + LocalTime.parse(time + ":00"));
                System.out.println("예약자 이름: " + name);
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                long id = Long.parseLong(params.split(",")[0]);

                ReservationResponseDto responseDto = reservationService.findReservationById(id);

                System.out.println("예약 번호: " + responseDto.getId());
                System.out.println("예약 날짜: " + responseDto.getDate());
                System.out.println("예약 시간: " + responseDto.getTime());
                System.out.println("예약자 이름: " + responseDto.getName());
                System.out.println("예약 테마 이름: " + responseDto.getThemeName());
                System.out.println("예약 테마 설명: " + responseDto.getThemeDesc());
                System.out.println("예약 테마 가격: " + responseDto.getThemePrice());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                long id = Long.parseLong(params.split(",")[0]);

                reservationService.cancelReservation(id);
                System.out.println("예약이 취소되었습니다.");
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

package roomescape;

import roomescape.exception.BusinessException;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.Theme;
import roomescape.reservation.dto.ReservationDto;
import roomescape.reservation.repository.console.ReservationRepositoryConsole;
import roomescape.reservation.repository.console.ThemeRepositoryConsole;
import roomescape.reservation.service.ReservationService;
import roomescape.reservation.service.ThemeService;

import java.util.List;
import java.util.Scanner;

public class ConsoleApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String SHOW = "show";
    private static final String QUIT = "quit";
    private static final ReservationService reservationService = new ReservationService(new ReservationRepositoryConsole());
    private static final ThemeService themeService = new ThemeService(new ThemeRepositoryConsole());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add {date},{time},{name},{theme_id} ex) add 2022-08-11,13:00,류성현,1");
            System.out.println("- 예약조회: find {id} ex) find 1");
            System.out.println("- 예약취소: delete {id} ex) delete 1");
            System.out.println("- 테마조회: show");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();

            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];
                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];
                String theme_id = params.split(",")[3];

                Long id;
                try {
                    id = reservationService.addReservation(new ReservationDto(date, time, name, Long.parseLong(theme_id)));
                } catch (BusinessException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                Reservation reservation = reservationService.getReservation(id);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 번호: " + reservation.getThemeId());

            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation;
                try {
                    reservation = reservationService.getReservation(id);
                } catch (BusinessException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 번호: " + reservation.getThemeId());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);

                try {
                    reservationService.removeReservation(id);
                } catch (BusinessException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                System.out.println("예약이 취소되었습니다.");
            }

            if (input.startsWith(SHOW)) {
                List<Theme> themes;

                themes = themeService.getAllTheme();

                for (Theme theme : themes) {
                    System.out.println("테마 번호: " + theme.getId());
                    System.out.println("테마 이름: " + theme.getName());
                    System.out.println("테마 설명: " + theme.getDesc());
                    System.out.println("테마 가격: " + theme.getPrice());
                    System.out.println();
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

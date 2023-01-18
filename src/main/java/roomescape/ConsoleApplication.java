package roomescape;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationDto;
import roomescape.reservation.dto.ThemeDto;
import roomescape.reservation.repository.console.ReservationRepositoryConsole;
import roomescape.reservation.service.ReservationService;

import java.util.Scanner;

public class ConsoleApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";
    private static final ReservationService reservationService = new ReservationService(new ReservationRepositoryConsole());
    private static final ThemeDto themeDto = new ThemeDto("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

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

                Long id;
                try {
                    id = reservationService.addReservation(new ReservationDto(date, time, name, 1L));
                } catch (IllegalArgumentException e) {
                    System.out.println("이미 예약된 시간입니다.");
                    continue;
                }
                Reservation reservation = reservationService.getReservation(id);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());

            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation;
                try {
                    reservation = reservationService.getReservation(id);
                } catch (IllegalArgumentException e) {
                    System.out.println("해당 예약은 없는 예약입니다.");
                    continue;
                }

                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 이름: " + themeDto.getName());
                System.out.println("예약 테마 설명: " + themeDto.getDesc());
                System.out.println("예약 테마 가격: " + themeDto.getPrice());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);

                try {
                    reservationService.removeReservation(id);
                } catch (IllegalArgumentException e) {
                    System.out.println("해당 예약은 없는 예약입니다.");
                    continue;
                }

                System.out.println("예약이 취소되었습니다.");
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

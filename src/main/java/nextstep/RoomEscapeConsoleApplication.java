package nextstep;

import nextstep.dao.ThemeDao;
import nextstep.dao.ThemeReservationDao;
import nextstep.dto.ReservationDetail;
import nextstep.dto.ReservationDto;
import nextstep.service.ThemeReservationService;
import nextstep.service.ThemeReservationServiceImpl;

import java.util.Scanner;

public class RoomEscapeConsoleApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    private static final ThemeReservationService themeReservationService = new ThemeReservationServiceImpl(new ThemeReservationDao(), new ThemeDao());

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

                ReservationDto reservationDto = new ReservationDto(date, time, name, null);

                Long reservationId = themeReservationService.reserve(reservationDto);
                ReservationDetail findReservation = themeReservationService.findById(reservationId);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + findReservation.getId());
                System.out.println("예약 날짜: " + findReservation.getDate());
                System.out.println("예약 시간: " + findReservation.getTime());
                System.out.println("예약자 이름: " + findReservation.getName());
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);
                ReservationDetail reservation = themeReservationService.findById(id);

                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 이름: " + reservation.getThemeName());
                System.out.println("예약 테마 설명: " + reservation.getThemeDesc());
                System.out.println("예약 테마 가격: " + reservation.getThemePrice());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);
                themeReservationService.cancelById(id);
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

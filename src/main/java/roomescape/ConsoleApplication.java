package roomescape;

import roomescape.reservation.dao.ReservationDaoConsole;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationDto;

import java.util.Scanner;

public class ConsoleApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    private static ReservationDaoConsole reservationDAO = new ReservationDaoConsole();

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

                if (reservationDAO.findReservationByDateAndTime(date, time).size() > 0) {
                    System.out.println("이미 예약된 시간입니다.");
                    continue;
                }

                Reservation reservation = reservationDAO.addReservation(new Reservation(new ReservationDto(date, time, name, 1L)));
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
                    reservation = reservationDAO.findReservationById(id).get(0);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("해당 예약은 없는 예약입니다.");
                    continue;
                }

                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                //System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
                //System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
                //System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);

                reservationDAO.removeReservation(id);

                System.out.println("예약이 취소되었습니다.");
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

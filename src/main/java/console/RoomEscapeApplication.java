package console;

import web.domain.Reservation;
import web.dto.request.ReservationRequestDTO;
import web.exception.NoSuchReservationException;

import java.util.Scanner;

public class RoomEscapeApplication {

    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";
    private static final ReservationDAO reservationDAO = new ReservationDAO();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add {date},{time},{name},{themId} ex) add 2022-08-11,13:00,류성현,1");
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

                ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(
                        date,
                        time,
                        name,
                        Long.valueOf(themeId)
                );

                Reservation reservation = reservationDAO.add(reservationRequestDTO);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation = reservationDAO.findById(id)
                        .orElseThrow(NoSuchReservationException::new);

                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("테마 번호: " + reservation.getThemeId());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                boolean isDeleted = reservationDAO.deleteById(id);
                if (isDeleted) {
                    System.out.println("정상적으로 삭제되었습니다.");
                }

                if (!isDeleted) {
                    System.out.println("존재하지 않는 예약입니다.");
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

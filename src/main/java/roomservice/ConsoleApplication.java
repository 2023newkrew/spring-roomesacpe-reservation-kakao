package roomservice;

import roomservice.domain.entity.Reservation;
import roomservice.repository.ReservationConsoleDao;
import roomservice.repository.ReservationDao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * ConsoleApplication Class is reservation system
 * operating on console system,
 * which is already made by instructor.<br>
 * I did not refactor this source, just little changes related to repository(connecting to DB).
 */
public class ConsoleApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    private static final ReservationDao reservationDao = new ReservationConsoleDao();

    // 처음부터 강의자로부터 만들어져 있던 소스였습니다.
    // 단순히 콘솔에서 예약 관련 기능을 실행할 수 있는 소스입니다.
    // database 연동을 제외한 부분은 거의 건들이지 않았습니다.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 테마 관련한 것은 다음 리뷰 요청 때 구현 예정
        // Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

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

                Reservation reservation = new Reservation(null,
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name,
                        null);

                reservation.setId(reservationDao.insertReservation(reservation));

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation = reservationDao.selectReservation(id);

                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
                System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
                System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                reservationDao.deleteReservation(id);
                System.out.println("예약이 취소되었습니다.");
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

package nextstep;

import nextstep.dao.ReservationDAO;
import nextstep.dao.SimpleReservationDAO;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ReservationRepositoryImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ReservationDAO dao = new SimpleReservationDAO();
        ReservationRepository repository = new ReservationRepositoryImpl(dao);

        Theme theme = new Theme(1L, "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add {date},{time},{name},{theme id} ex) add 2022-08-11,13:00,류성현,1");
            System.out.println("- 예약조회: find {id} ex) find 1");
            System.out.println("- 예약취소: delete {id} ex) delete 1");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];
                Long theme_id = Long.parseLong(params.split(",")[3]);

                Reservation reservation = new Reservation(
                        null,
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name,
                        theme_id
                );
                Long id = repository.insertIfNotExistsDateTime(reservation);
                reservation = repository.getById(id);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println(("예약 테마 번호 : " + reservation.getTheme_id()));
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation = repository.getById(id);
                if (reservation != null) {
                    System.out.println("예약 번호: " + reservation.getId());
                    System.out.println("예약 날짜: " + reservation.getDate());
                    System.out.println("예약 시간: " + reservation.getTime());
                    System.out.println("예약자 이름: " + reservation.getName());
//                    System.out.println("예약 테마 이름: " + reservation.getTheme()
//                            .getName());
//                    System.out.println("예약 테마 설명: " + reservation.getTheme()
//                            .getDesc());
//                    System.out.println("예약 테마 가격: " + reservation.getTheme()
//                            .getPrice());
                    System.out.println("예약 테마 번호: " + reservation.getTheme_id());
                }
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                if (repository.deleteById(id)) {
                    System.out.println("예약이 취소되었습니다.");
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

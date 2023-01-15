package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.exception.NoSuchReservationException;
import roomescape.reservation.repository.ReservationJdbcRepository;
import roomescape.theme.domain.Theme;

public class RoomEscapeConsoleApplication {

    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";
    private static final ReservationJdbcRepository RESERVATION_JDBC_REPOSITORY = new ReservationJdbcRepository();

    public static void main(String[] args) {
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

                Reservation reservation = Reservation.builder()
                        .date(LocalDate.parse(date))
                        .time(LocalTime.parse(time + ":00"))
                        .name(name)
                        .themeId(1L)
                        .build();

                final Long newReservationId = RESERVATION_JDBC_REPOSITORY.save(reservation);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + newReservationId);
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation = RESERVATION_JDBC_REPOSITORY.findById(id)
                        .orElseThrow(NoSuchReservationException::new);

                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 이름: " + theme.getName());
                System.out.println("예약 테마 설명: " + theme.getDesc());
                System.out.println("예약 테마 가격: " + theme.getPrice());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                boolean isDeleted = RESERVATION_JDBC_REPOSITORY.deleteById(id);
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

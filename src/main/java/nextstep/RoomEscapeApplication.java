package nextstep;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import web.entity.Reservation;
import web.entity.Theme;
import web.reservation.repository.DatabaseReservationRepository;
import web.reservation.repository.ReservationRepository;
import web.theme.repository.ThemeRepository;

import javax.sql.DataSource;
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
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .build();
        ReservationRepository reservationRepository = new DatabaseReservationRepository(dataSource);
        ThemeRepository themeRepository = new ThemeRepository(dataSource);
        Long reservationIdIndex = 0L;

        Theme defaultTheme = Theme.of(0L, "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        themeRepository.save(defaultTheme);

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

                Reservation reservation = Reservation.of(
                        ++reservationIdIndex,
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name,
                        defaultTheme.getId()
                );

                reservationRepository.save(reservation);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation = reservationRepository.findById(id)
                        .orElseThrow(RuntimeException::new);
                Theme theme = themeRepository.findById(reservation.getThemeId())
                        .orElseThrow();

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

                if (reservationRepository.delete(id) != 0) {
                    System.out.println("예약이 취소되었습니다.");
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

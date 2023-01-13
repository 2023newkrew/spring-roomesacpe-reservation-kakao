package roomescape.console;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import roomescape.dao.reservation.ConsoleReservationDAO;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dao.theme.ConsoleThemeDAO;
import roomescape.dao.theme.ThemeDAO;
import roomescape.dto.Reservation;
import roomescape.dto.Theme;

@Profile("!test")
@Component
public class RoomEscapeConsoleApplication implements CommandLineRunner {

    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static final String RESERVATION_ADD = "res_add";
    private static final String RESERVATION_FIND = "res_find";
    private static final String RESERVATION_DELETE = "res_delete";
    private static final String THEME_ADD = "the_add";
    private static final String THEME_LIST = "the_list";
    private static final String THEME_DELETE = "the_delete";

    private static final String QUIT = "quit";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservationDAO reservationDAO = new ConsoleReservationDAO(URL, USER, PASSWORD);
        ThemeDAO themeDAO = new ConsoleThemeDAO(URL, USER, PASSWORD);

//        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: res_add {date},{time},{name},{theme_id} ex) res_add 2022-08-11,13:00,류성현,1");
            System.out.println("- 예약조회: res_find {id} ex) res_find 1");
            System.out.println("- 예약취소: res_delete {id} ex) res_delete 1");
            System.out.println("- 테마추가: the_add {name},{desc},{price} ex) theme add 워너고홈,병맛 어드벤처 회사 코믹물,29000");
            System.out.println("- 테마조회: the_list");
            System.out.println("- 테마삭제: the_delete {id} ex) theme delete 1");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(RESERVATION_ADD)) {
                String params = input.split(" ")[1];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];
                long theme_id = Long.parseLong(params.split(",")[3]);

                Reservation reservation = new Reservation(
                        LocalDate.parse(date), LocalTime.parse(time + ":00"), name, theme_id);
                long id = reservationDAO.create(reservation);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + id);
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 번호: " + reservation.getThemeId());
            }

            if (input.startsWith(RESERVATION_FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation = reservationDAO.find(id);

                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 번호: " + reservation.getThemeId());
//                System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
//                System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
//                System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
            }

            if (input.startsWith(RESERVATION_DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                reservationDAO.remove(id);
                System.out.println("예약이 취소되었습니다.");
            }

            if(input.startsWith(THEME_ADD)) {
                String params = input.split(" ")[1];

                String name = params.split(",")[0];
                String desc = params.split(",")[1];
                int price = Integer.parseInt(params.split(",")[2]);

                Theme theme = new Theme(name, desc, price);
                Long id = themeDAO.create(theme);

                System.out.println("테마가 추가되었습니다.");
                System.out.println("- 테마 번호: " + id);
                System.out.println("- 테마 이름: " + theme.getName());
                System.out.println("- 테마 설명: " + theme.getDesc());
                System.out.println("- 테마 가격: " + theme.getPrice());
            }

            if(input.startsWith(THEME_LIST)) {
                for (Theme theme : themeDAO.list()) {
                    System.out.println("- 테마 번호: " + theme.getId());
                    System.out.println("- 테마 이름: " + theme.getName());
                    System.out.println("- 테마 설명: " + theme.getDesc());
                    System.out.println("- 테마 가격: " + theme.getPrice());
                }
            }

            if(input.startsWith(THEME_DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                themeDAO.remove(id);
                System.out.println("테마가 삭제되었습니다.");
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }

    @Override
    public void run(String... args) {
        main(args);
    }
}

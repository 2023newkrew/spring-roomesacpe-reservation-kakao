package nextstep;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.theme.Theme;
import nextstep.repository.ConsoleReservationRepo;
import nextstep.repository.ConsoleThemeRepo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class ConsoleMain {
    private static final String RESERVATION_ADD = "reservation add";
    private static final String RESERVATION_FIND = "reservation find";
    private static final String RESERVATION_DELETE = "reservation delete";
    private static final String THEME_ADD = "theme add";
    private static final String THEME_FIND = "theme find";
    private static final String THEME_DELETE = "theme delete";
    private static final String QUIT = "quit";

    private static final ConsoleReservationRepo consoleReservationRepo = new ConsoleReservationRepo();
    private static final ConsoleThemeRepo consoleThemeRepo = new ConsoleThemeRepo();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: reservation add {date},{time},{name} ex) reservation add 2022-08-11,13:00,류성현");
            System.out.println("- 예약조회: reservation find {id} ex) reservation find 1");
            System.out.println("- 예약취소: reservation delete {id} ex) reservation delete 1");
            System.out.println("- 테마추가: theme add {name},{desc},{price} ex) theme add 워너고홈,병맛 어드벤처 회사 코믹물,29000");
            System.out.println("- 테마조회: theme find");
            System.out.println("- 테마삭제: theme delete {id} ex) theme delete 1");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(RESERVATION_ADD)) {
                String[] params = input.split(" ")[2].split(",");

                String date = params[0];
                String time = params[1];
                String name = params[2];

                Reservation newReservation = new Reservation(LocalDate.parse(date), LocalTime.parse(time + ":00"), name, 1L);
                long id = consoleReservationRepo.save(newReservation);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + id);
                System.out.println("예약 날짜: " + newReservation.getDate());
                System.out.println("예약 시간: " + newReservation.getTime());
                System.out.println("예약자 이름: " + newReservation.getName());
            }

            if (input.startsWith(RESERVATION_FIND)) {
                String param = input.split(" ")[2];
                Long id = Long.parseLong(param);

                Reservation reservation = consoleReservationRepo.findById(id);

                if (reservation == null) {
                    System.out.println("해당하는 예약이 없습니다");
                    continue;
                }
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마: " + reservation.getThemeId());
            }

            if (input.startsWith(RESERVATION_DELETE)) {
                String param = input.split(" ")[2];
                Long id = Long.parseLong(param);

                if (consoleReservationRepo.delete(id) == 1) {
                    System.out.println("예약이 취소되었습니다.");
                }
            }

            if (input.startsWith(THEME_ADD)) {
                String[] params = input.substring(THEME_ADD.length() + 1).split(",");

                String name = params[0];
                String desc = params[1];
                String price = params[2];

                Theme newTheme = new Theme(name, desc, Integer.parseInt(price));
                long id = consoleThemeRepo.save(newTheme);

                System.out.println("테마가 추가되었습니다.");
                System.out.println("테마 번호: " + id);
                System.out.println("테마 이름: " + newTheme.getName());
                System.out.println("테마 설명: " + newTheme.getDesc());
                System.out.println("테마 가격: " + newTheme.getPrice());
            }

            if (input.startsWith(THEME_FIND)) {
                List<Theme> themes = consoleThemeRepo.findAll();

                if (themes.isEmpty()) {
                    System.out.println("테마가 없습니다");
                    continue;
                }
                for (Theme theme : themes) {
                    System.out.println("테마 번호: " + theme.getId());
                    System.out.println("테마 이름: " + theme.getName());
                    System.out.println("테마 설명: " + theme.getDesc());
                    System.out.println("테마 가격: " + theme.getPrice() + "\n");
                }
            }

            if (input.startsWith(THEME_DELETE)) {
                String param = input.split(" ")[2];
                Long id = Long.parseLong(param);

                if (consoleThemeRepo.delete(id) == 1) {
                    System.out.println("테마가 삭제되었습니다.");
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

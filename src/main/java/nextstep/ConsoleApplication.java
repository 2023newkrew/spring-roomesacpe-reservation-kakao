package nextstep;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.repository.reservation.ConsoleReservationRepository;
import nextstep.repository.theme.ConsoleThemeRepository;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class ConsoleApplication {

    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";
    private static final String THEME = "theme";
    private static final String LIST = "list";
    private static final ReservationService reservationService = new ReservationService(new ConsoleReservationRepository(), new ConsoleThemeRepository());
    private static final ThemeService themeService = new ThemeService(new ConsoleThemeRepository(), new ConsoleReservationRepository());

    public static void main() {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            Theme theme = null;

            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add {date},{time},{themeId},{name} ex) add 2022-08-11,13:00,1,류성현");
            System.out.println("- 예약조회: find {id} ex) find 1");
            System.out.println("- 예약취소: delete {id} ex) delete 1");
            System.out.println("- 테마 관리: theme");
            System.out.println("- 종료: quit");


            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];
                Long themeId = Long.valueOf(params.split(",")[3]);

                try {
                    theme = themeService.findById(themeId);
                    Long id = reservationService.createReservation(LocalDate.parse(date),
                            LocalTime.parse(time + ":00"), name, theme);
                    Reservation reservation = reservationService.findById(id);

                    System.out.println("예약이 등록되었습니다.");
                    System.out.println("예약 번호: " + reservation.getId());
                    System.out.println("예약 날짜: " + reservation.getDate());
                    System.out.println("예약 시간: " + reservation.getTime());
                    System.out.println("예약자 이름: " + reservation.getName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);

                try {
                    Reservation reservation = reservationService.findById(id);

                    System.out.println("예약 번호: " + reservation.getId());
                    System.out.println("예약 날짜: " + reservation.getDate());
                    System.out.println("예약 시간: " + reservation.getTime());
                    System.out.println("예약자 이름: " + reservation.getName());
                    System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
                    System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
                    System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                try {
                    reservationService.deleteReservation(id);
                    System.out.println("예약이 취소되었습니다.");
                } catch (Exception e) {
                    System.out.println("예약을 취소할 수 없습니다.");
                }
            }

            if (input.startsWith(THEME)) {
                System.out.println("- 테마목록 조회: find list ex) find list");
                System.out.println("- 테마 조회: find list ex) find 1");
                System.out.println("- 테마 생성: add {name},{desc},{price} ex) add 워너고홈, 병맛 어드벤처 회사 코믹물, 29000");
                System.out.println("- 테마 삭제: delete {id} ex) delete 1");
                System.out.println("- 테마 종료: quit");
                input = scanner.nextLine();

                if (input.startsWith(ADD)) {
                    String params = input.split(" ")[1];

                    String name = params.split(",")[0];
                    String desc = params.split(",")[1];
                    Integer price = Integer.valueOf(params.split(",")[2]);

                    try {
                        theme = new Theme(name, desc, price);
                        Long id = themeService.createTheme(theme);
                        theme = themeService.findById(id);

                        System.out.println("테마가 등록되었습니다.");
                        System.out.println("테마 번호: " + theme.getId());
                        System.out.println("테마 이름: " + theme.getName());
                        System.out.println("테마 설명: " + theme.getDesc());
                        System.out.println("테마 가격: " + theme.getPrice());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (input.startsWith(FIND)) {
                    String params = input.split(" ")[1];
                    if(params.equals(LIST)){
                        try {
                            for (Theme one : themeService.findAll()) {
                                System.out.println("테마 번호: " + one.getId());
                                System.out.println("테마 이름: " + one.getName());
                                System.out.println("테마 설명: " + one.getDesc());
                                System.out.println("테마 가격: " + one.getPrice());
                                System.out.println();
                            }
                            continue;
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }

                    Long id = Long.parseLong(params.split(",")[0]);
                    try {
                        theme = themeService.findById(id);
                        System.out.println("테마 번호: " + theme.getId());
                        System.out.println("테마 이름: " + theme.getName());
                        System.out.println("테마 설명: " + theme.getDesc());
                        System.out.println("테마 가격: " + theme.getPrice());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (input.startsWith(DELETE)) {
                    String params = input.split(" ")[1];
                    Long id = Long.parseLong(params.split(",")[0]);

                    try {
                        themeService.deleteById(id);
                        System.out.println("테마가 삭제되었습니다.");
                    } catch (Exception e) {
                        System.out.println("테마가 존재하지 않습니다.");
                    }
                }

                if (input.equals(QUIT)) {
                    continue;
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

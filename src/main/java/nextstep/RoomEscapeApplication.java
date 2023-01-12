package nextstep;

import roomescape.domain.Reservation;
import roomescape.domain.Theme;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ThemeRequest;
import roomescape.repository.ReservationConsoleRepository;
import roomescape.repository.ThemeConsoleRepository;
import roomescape.service.ReservationService;
import roomescape.service.ThemeService;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String ADD_RESERVATION = "add reservation";
    private static final String FIND_RESERVATION = "find reservation";
    private static final String DELETE_RESERVATION = "delete reservation";
    private static final String ADD_THEME = "add theme";
    private static final String FIND_THEME = "find theme";
    private static final String UPDATE_THEME = "update theme";
    private static final String DELETE_THEME = "delete theme";
    private static final String QUIT = "quit";
    private static final ReservationService reservationService = new ReservationService(new ReservationConsoleRepository(), new ThemeConsoleRepository());
    private static final ThemeService themeService = new ThemeService(new ThemeConsoleRepository());
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add reservation {date},{time},{name},{theme_id} ex) add reservation 2022-08-11,13:00,류성현,1");
            System.out.println("- 예약조회: find reservation {id} ex) find reservation 1");
            System.out.println("- 예약취소: delete reservation {id} ex) delete reservation 1");
            System.out.println("- 테마 추가하기: add theme {name},{desc},{price} ex) add theme 워너고홈,병맛 어드벤처 회사 코믹물,29000");
            System.out.println("- 테마 조회하기: find theme {id} ex) find theme 1");
            System.out.println("- 테마 수정하기: update theme {id},{name},{desc},{price} ex) update theme 위너고홈,병맛 어드벤처 회사 코믹물,30000");
            System.out.println("- 테마 삭제하기: delete theme {id} ex) delete theme 1");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();

            if (input.startsWith(ADD_RESERVATION)) {
                String params = input.split(" ")[2];
                String[] info = params.split(",");
                String date = info[0];
                String time = info[1];
                String name = info[2];
                Long theme_id = Long.parseLong(info[3]);

                ReservationRequest reservationRequest = new ReservationRequest(date, time, name, theme_id);
                Reservation reservation = reservationService.createReservation(reservationRequest);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
            }

            if (input.startsWith(FIND_RESERVATION)) {
                String params = input.split(" ")[2];
                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation = null;
                try {
                    reservation = reservationService.showReservation(id);
                } catch (NoSuchElementException e) {
                    System.out.println("WARNING: 해당 예약은 없는 예약입니다.");
                    e.printStackTrace();
                    continue;
                }

                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
                System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
                System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
            }

            if (input.startsWith(DELETE_RESERVATION)) {
                String params = input.split(" ")[2];
                Long id = Long.parseLong(params.split(",")[0]);

                reservationService.deleteReservation(id);

                System.out.println("예약이 취소되었습니다.");
            }

            if (input.startsWith(ADD_THEME)) {
                String params = input.split(" ")[2];
                String[] info = params.split(",");
                String name = info[0];
                String desc = info[1];
                Integer price = Integer.parseInt(info[2]);

                ThemeRequest themeRequest = new ThemeRequest(name, desc, price);
                Theme theme = themeService.createTheme(themeRequest);

                System.out.println("테마가 새로 등록되었습니다.");
                System.out.println("테마 이름: " + theme.getName());
                System.out.println("테마 설명: " + theme.getDesc());
                System.out.println("테마 가격: " + theme.getPrice());
            }

            if (input.startsWith(FIND_THEME)) {
                String params = input.split(" ")[2];
                Long id = Long.parseLong(params.split(",")[0]);

                Theme theme = null;
                try {
                    theme = themeService.showTheme(id);
                } catch (NoSuchElementException e) {
                    System.out.println("WARNING: 해당 예약은 없는 예약입니다.");
                    e.printStackTrace();
                    continue;
                }

                System.out.println("테마 번호: " + theme.getId());
                System.out.println("테마 이름: " + theme.getName());
                System.out.println("테마 설명: " + theme.getDesc());
                System.out.println("테마 가격: " + theme.getPrice());
            }

            if (input.startsWith(UPDATE_THEME)) {
                String params = input.split(" ")[2];
                String[] info = params.split(",");
                Long id = Long.parseLong(info[0]);
                String name = info[1];
                String desc = info[2];
                Integer price = Integer.parseInt(info[3]);

                Theme theme = new Theme(id, name, desc, price);
                themeService.updateTheme(theme);

                System.out.println("테마가 수정되었습니다.");
                System.out.println("테마 번호: " + theme.getId());
                System.out.println("테마 이름: " + theme.getName());
                System.out.println("테마 설명: " + theme.getDesc());
                System.out.println("테마 가격: " + theme.getPrice());
            }

            if (input.startsWith(DELETE_THEME)) {
                String params = input.split(" ")[2];
                Long id = Long.parseLong(params.split(",")[0]);

                themeService.deleteTheme(id);

                System.out.println("테마가 삭제되었습니다.");
            }

            if (input.equals(QUIT)) {
                break;
            }
        }

    }
}

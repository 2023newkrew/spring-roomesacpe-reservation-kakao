package nextstep;

import nextstep.domain.dto.reservation.CreateReservationDto;
import nextstep.domain.dto.theme.CreateThemeDto;
import nextstep.domain.dto.theme.UpdateThemeDto;
import nextstep.domain.reservation.Reservation;
import nextstep.domain.theme.Theme;
import nextstep.exception.DeleteReservationFailureException;
import nextstep.exception.DuplicateTimeReservationException;
import nextstep.exception.NoReservationException;
import nextstep.repository.reservation.ConsoleReservationRepository;
import nextstep.repository.theme.ConsoleThemeRepository;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;

import java.util.List;
import java.util.Scanner;

public class RoomEscapeConsoleApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";
    private static final String THEME = "theme";
    private static final String LIST = "list";
    private static final String UPDATE = "update";

    private static final ReservationService reservationService = new ReservationService(new ConsoleReservationRepository());
    private static final ThemeService themeService = new ThemeService(new ConsoleThemeRepository());
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            String input = getInput();
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];

                insertReservation(date, time, name);
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                showReservationInfo(id);
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                deleteReservation(id);
            }

            if (input.startsWith(THEME)) {
                String themeOrder = input.split(" ")[1];

                if (themeOrder.startsWith(LIST)) {
                    showThemeList();
                }

                if (themeOrder.startsWith(ADD)) {
                    String[] params = input.split(" ")[2].split(",");
                    addTheme(params);
                }

                if (themeOrder.startsWith(UPDATE)) {
                    Long id = Long.parseLong(input.split(" ")[2]);
                    String[] params = input.split(" ")[3].split(",");
                    updateTheme(id, params);
                }

                if (themeOrder.startsWith(DELETE)) {
                    Long id = Long.parseLong(input.split(" ")[2]);
                    deleteTheme(id);
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }

    private static void deleteTheme(Long id) {
        try {
            themeService.deleteTheme(id);
            System.out.println("테마 삭제 완료");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void updateTheme(Long id, String[] params) {
        try {
            themeService.updateTheme(new UpdateThemeDto(
                    id,
                    params[0],
                    params[1],
                    Integer.parseInt(params[2])
            ));
            System.out.println("테마 업데이트 성공");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void addTheme(String[] params) {
        try {
            themeService.addTheme(new CreateThemeDto(
                    params[0],
                    params[1],
                    Integer.parseInt(params[2])
            ));
            System.out.println("테마가 추가되었습니다.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void showThemeList() {
        try {
            List<Theme> themeList = themeService.getAllThemes();
            for (Theme theme : themeList) {
                System.out.println();
                System.out.println("테마 번호 : " + theme.getId());
                System.out.println("테마 이름 : " + theme.getName());
                System.out.println("테마 설명 : " + theme.getDesc());
                System.out.println("테마 가격 : " + theme.getPrice());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deleteReservation(Long id) {
        try {
            reservationService.deleteReservation(id);
        } catch (DeleteReservationFailureException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void showReservationInfo(Long id) {
        try {
            Reservation reservation = reservationService.getReservation(id);
            System.out.println("예약 번호: " + reservation.getId());
            System.out.println("예약 날짜: " + reservation.getDate());
            System.out.println("예약 시간: " + reservation.getTime());
            System.out.println("예약자 이름: " + reservation.getName());
            System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
            System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
            System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
        } catch (NoReservationException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void insertReservation(String date, String time, String name) {
        CreateReservationDto createReservationDto = CreateReservationDto.create(date, time, name, 1l);
        try {
            long id = reservationService.addReservation(createReservationDto);
            System.out.println("예약이 등록되었습니다.");
            System.out.println("예약 번호: " + id);
            System.out.println("예약 날짜: " + date);
            System.out.println("예약 시간: " + time);
            System.out.println("예약자 이름: " + name);
        } catch (DuplicateTimeReservationException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static String getInput() {
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
        System.out.println(" --- ");
        System.out.println("- 테마조회: theme list");
        System.out.println("- 테마추가: theme add {name},{desc},{price} ex) theme add 테마이름,테마설명,30000");
        System.out.println("- 테마수정: theme update {id} {name},{desc},{price} ex) theme update 1 새이름,새설명,20000");
        System.out.println("- 테마삭제: theme delete {id} ex) theme delete 1");
        System.out.println("- 종료: quit");

        String input = scanner.nextLine();
        return input;
    }
}

package nextstep;

import domain.Reservation;
import domain.Theme;
import kakao.dto.request.CreateReservationRequest;
import kakao.dto.request.UpdateThemeRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";
    private static final String ADD_THEME = "addTheme";
    private static final String FIND_THEME = "findTheme";
    private static final String UPDATE_THEME = "updateTheme";
    private static final String DELETE_THEME = "deleteTheme";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservationDAO reservationDAO = new ReservationDAO();
        ThemeDAO themeDAO = new ThemeDAO();


        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add {date},{time},{name},{theme_id} ex) add 2022-08-11,13:00,류성현,1");
            System.out.println("- 예약조회: find {id} ex) find 1");
            System.out.println("- 예약취소: delete {id} ex) delete 1");
            System.out.println("- 테마 생성하기: addTheme {name},{desc},{price} ex) addTheme themeName, themeDesc, 2000");
            System.out.println("- 테마 조회하기: findTheme {id} ex) findTheme 1");
            System.out.println("- 테마 수정하기: updateTheme {id},{name},{desc},{price} ex) updateTheme 1, themeName, themeDesc, 2000");
            System.out.println("- 테마 삭제하기: deleteTheme {id} ex) deleteTheme 1");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(ADD_THEME)) {
                String params = input.split(" ")[1];

                String name = params.split(",")[0];
                String desc = params.split(",")[1];
                Integer price = Integer.parseInt(params.split(",")[2]);

                themeDAO.save(new Theme(name, desc, price));
            } else if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];
                Long theme_id = Long.parseLong(params.split(",")[3]);

                CreateReservationRequest reservationRequest = new CreateReservationRequest(
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name,
                        theme_id
                );

                long reservationId = reservationDAO.addReservation(reservationRequest);
                if (reservationId >= 0) {
                    System.out.println("예약이 등록되었습니다.");
                    System.out.println("예약 번호: " + reservationId);
                    System.out.println("예약 날짜: " + reservationRequest.date);
                    System.out.println("예약 시간: " + reservationRequest.time);
                    System.out.println("예약자 이름: " + reservationRequest.name);
                }
            }

            if (input.startsWith(FIND_THEME)) {
                String params = input.split(" ")[1];

                long id = Long.parseLong(params.split(",")[0]);

                Theme theme = themeDAO.findById(id);
                if (Objects.isNull(theme)) throw new RuntimeException("해당 ID의 테마가 존재하지 않습니다");

                System.out.println("테마 id: " + theme.getId());
                System.out.println("테마 이름: " + theme.getName());
                System.out.println("테마 설명: " + theme.getDesc());
                System.out.println("테마 가격: " + theme.getPrice());

            } else if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation = reservationDAO.findById(id);
                if (Objects.isNull(reservation)) throw new RuntimeException("해당 ID의 예약이 존재하지 않습니다.");


                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
                System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
                System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
            }

            if (input.startsWith(UPDATE_THEME)) {
                String params = input.split(" ")[1];

                long id = Long.parseLong(params.split(",")[0]);
                String name = params.split(",")[1];
                String desc = params.split(",")[2];
                Integer price = Integer.parseInt(params.split(",")[3]);

                if (!reservationDAO.findByThemeId(id).isEmpty()) throw new RuntimeException("해당 테마는 이미 예약되어 있습니다");
                themeDAO.update(new UpdateThemeRequest(id, name, desc, price));
            }

            if (input.startsWith(DELETE_THEME)) {
                String params = input.split(" ")[1];

                long id = Long.parseLong(params.split(",")[0]);

                if (themeDAO.delete(id) > 0)
                    System.out.println("테마가 삭제되었습니다");

            } else if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                if (reservationDAO.delete(id) > 0) {
                    System.out.println("예약이 취소되었습니다.");
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

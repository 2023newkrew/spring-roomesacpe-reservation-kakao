package nextstep.roomescape;

import nextstep.roomescape.reservation.controller.dto.ReservationResponseDTO;
import nextstep.roomescape.reservation.repository.ReservationRepositoryJdbcImpl;
import nextstep.roomescape.reservation.service.ReservationServiceImpl;
import nextstep.roomescape.reservation.controller.dto.ReservationRequestDTO;
import nextstep.roomescape.reservation.model.Theme;
import nextstep.roomescape.reservation.exception.DeleteReservationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
        ReservationServiceImpl reservationServiceImpl = new ReservationServiceImpl(new ReservationRepositoryJdbcImpl(new JdbcTemplate(dataSource())));


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

                ReservationRequestDTO reservation = new ReservationRequestDTO(
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name,
                        theme
                );
                try {
                    ReservationResponseDTO createReservation = reservationServiceImpl.create(reservation);

                    System.out.println("예약이 등록되었습니다.");
                    printReservation(createReservation);
                } catch (ClassCastException e){
                    System.out.println(e.getMessage());
                }

            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);
                ReservationResponseDTO reservation = reservationServiceImpl.findById(id);

                printReservation(reservation);
                printTheme(reservation);
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);
                try {
                    if (reservationServiceImpl.delete(id)) {
                        System.out.println("예약이 취소되었습니다.");
                    }
                } catch (DeleteReservationException e){
                    System.out.println(e.getMessage());
                }

            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }

    public static DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/test");
        return dataSource;
    }

    public static void printReservation(ReservationResponseDTO reservation){
        System.out.println("예약 번호: " + reservation.getId());
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
    }

    public static void printTheme(ReservationResponseDTO reservation){
        System.out.println("예약 테마 이름: " + reservation.getThemeName());
        System.out.println("예약 테마 설명: " + reservation.getThemeDesc());
        System.out.println("예약 테마 가격: " + reservation.getThemePrice());
    }
}

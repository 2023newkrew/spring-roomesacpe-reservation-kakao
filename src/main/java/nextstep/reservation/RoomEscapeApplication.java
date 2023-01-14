package nextstep.reservation;

import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.dto.ReservationResponse;
import nextstep.reservation.dto.ThemeRequest;
import nextstep.reservation.dto.ThemeResponse;
import nextstep.reservation.exception.RoomEscapeException;
import nextstep.reservation.repository.JdbcReservationRepository;
import nextstep.reservation.repository.JdbcThemeRepository;
import nextstep.reservation.service.ReservationService;
import nextstep.reservation.service.ThemeService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String FIND_ALL = "findAll";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ThemeService themeService = new ThemeService(new JdbcThemeRepository(new JdbcTemplate(dataSource())));
        ReservationService reservationService = new ReservationService(new JdbcReservationRepository(new JdbcTemplate(dataSource())), themeService);


        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add reservation {date},{time},{name},{themeId} ex) add reservation 2022-08-11,13:00,류성현,1");
            System.out.println("- 예약조회: find {id} ex) find 1");
            System.out.println("- 예약취소: delete {id} ex) delete 1");
            System.out.println("- 테마추가하기: add theme {name},{desc},{price} ex) add theme 호러,매우무서운,25000");
            System.out.println("- 테마전체조회하기: findAll theme");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                String target = input.split(" ")[1];
                if (target.equals("reservation")) {
                    String params = input.split(" ")[2];

                    String date = params.split(",")[0];
                    String time = params.split(",")[1];
                    String name = params.split(",")[2];
                    String themeId = params.split(",")[3];

                    ReservationRequest reservationRequest = ReservationRequest.builder()
                            .date(LocalDate.parse(date))
                            .time(LocalTime.parse(time + ":00"))
                            .name(name)
                            .themeId(Long.valueOf(themeId))
                            .build();
                    try {
                        ReservationResponse createdReservation = reservationService.registerReservation(reservationRequest);
                        System.out.println("예약이 등록되었습니다.");
                        System.out.println("예약 번호: " + createdReservation.getId());
                        System.out.println("예약 날짜: " + createdReservation.getDate());
                        System.out.println("예약 시간: " + createdReservation.getTime());
                        System.out.println("예약자 이름: " + createdReservation.getName());
                        System.out.println("테마 번호: " + createdReservation.getThemeId());
                        System.out.println("테마 이름: " + createdReservation.getThemeName());
                        System.out.println("테마 설명: " + createdReservation.getThemeDesc());
                        System.out.println("테마 가격: " + createdReservation.getThemePrice());
                    } catch (RoomEscapeException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (target.equals("theme")) {
                    String params = input.split(" ")[2];

                    String name = params.split(",")[0];
                    String desc = params.split(",")[1];
                    String price = params.split(",")[2];

                    ThemeRequest themeRequest = ThemeRequest.builder()
                            .name(name)
                            .desc(desc)
                            .price(Integer.valueOf(price))
                            .build();

                    try {

                        ThemeResponse themeResponse = themeService.registerTheme(themeRequest);

                        System.out.println("테마가 등록되었습니다.");
                        System.out.println("테마 번호: " + themeResponse.getId());
                        System.out.println("테마 이름: " + themeResponse.getName());
                        System.out.println("테마 설명: " + themeResponse.getDesc());
                        System.out.println("테마 가격: " + themeResponse.getPrice());
                    } catch (RoomEscapeException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            if (input.split(" ")[0].equals(FIND)) {
                String params = input.split(" ")[1];
                long id = Long.parseLong(params.split(",")[0]);
                try {
                    ReservationResponse reservationResponse = reservationService.findById(id);

                    System.out.println("예약 번호: " + reservationResponse.getId());
                    System.out.println("예약 날짜: " + reservationResponse.getDate());
                    System.out.println("예약 시간: " + reservationResponse.getTime());
                    System.out.println("예약자 이름: " + reservationResponse.getName());
                    System.out.println("예약 테마 ID: " + reservationResponse.getThemeId());
                    System.out.println("예약 테마 이름: " + reservationResponse.getThemeName());
                    System.out.println("예약 테마 설명: " + reservationResponse.getThemeDesc());
                    System.out.println("예약 테마 가격: " + reservationResponse.getThemePrice());
                } catch (RoomEscapeException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (input.startsWith(FIND_ALL)) {
                List<ThemeResponse> themes = themeService.findAll();
                for (ThemeResponse themeResponse : themes) {
                    System.out.println("예약 테마 ID: " + themeResponse.getId());
                    System.out.println("예약 테마 이름: " + themeResponse.getName());
                    System.out.println("예약 테마 설명: " + themeResponse.getDesc());
                    System.out.println("예약 테마 가격: " + themeResponse.getPrice());
                    System.out.println();
                }
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                long id = Long.parseLong(params.split(",")[0]);

                if (reservationService.delete(id)) {
                    System.out.println("예약이 취소되었습니다.");
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
}
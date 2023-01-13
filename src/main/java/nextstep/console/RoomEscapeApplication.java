package nextstep.console;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Scanner;
import javax.sql.DataSource;
import nextstep.exception.ReservationDuplicateException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.repository.JdbcReservationRepository;
import nextstep.repository.ReservationRepository;

public class RoomEscapeApplication {

    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataSource dataSource = createHikariDataSource();
        createDataBaseTables(dataSource);

        ReservationRepository reservationRepository = new JdbcReservationRepository(dataSource);

        while (true) {
            String input = getInput(scanner);
            try {
                if (input.startsWith(ADD)) {
                    Reservation reservation = addReservation(input, reservationRepository);
                    printReservation(reservation);
                }
                if (input.startsWith(FIND)) {
                    Reservation reservation = findReservation(input, reservationRepository);
                    printReservation(reservation);
                    printReservationTheme(reservation.getTheme());
                }
                if (input.startsWith(DELETE)) {
                    deleteReservation(input, reservationRepository);
                }
                if (input.equals(QUIT)) {
                    break;
                }
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }
        scanner.close();
    }

    private static void createDataBaseTables(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS RESERVATION\n"
                    + "(\n"
                    + "    id          bigint not null auto_increment,\n"
                    + "    date        date,\n"
                    + "    time        time,\n"
                    + "    name        varchar(20),\n"
                    + "    theme_name  varchar(20),\n"
                    + "    theme_desc  varchar(255),\n"
                    + "    theme_price int,\n"
                    + "    primary key (id)\n"
                    + ");");

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static HikariDataSource createHikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:~/test");
        config.setUsername("sa");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    public static String getInput(Scanner scanner) {
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
        System.out.println("- 종료: quit");

        return scanner.nextLine();
    }

    public static Reservation addReservation(String input, ReservationRepository reservationRepository) {
        String params = input.split(" ")[1];
        String date = params.split(",")[0];
        String time = params.split(",")[1];
        String name = params.split(",")[2];

        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

        if (reservationRepository.existsByDateAndTime(LocalDate.parse(date), LocalTime.parse(time + ":00"))) {
            throw new ReservationDuplicateException();
        }

        Reservation reservation = reservationRepository.save(new Reservation(
                0L,
                LocalDate.parse(date),
                LocalTime.parse(time + ":00"),
                name,
                theme
        ));
        System.out.println("예약이 등록되었습니다.");
        return reservation;
    }

    public static Reservation findReservation(String input, ReservationRepository reservationRepository) {
        String params = input.split(" ")[1];
        Long id = Long.parseLong(params.split(",")[0]);

        Optional<Reservation> result = reservationRepository.findById(id);
        if (result.isEmpty()) {
            throw new ReservationNotFoundException(id);
        }
        return result.get();
    }

    public static void deleteReservation(String input, ReservationRepository reservationRepository) {
        String params = input.split(" ")[1];
        Long id = Long.parseLong(params.split(",")[0]);

        reservationRepository.deleteById(id);

        System.out.println("예약이 취소되었습니다.");
    }

    public static void printReservation(Reservation reservation) {
        System.out.println("예약 번호: " + reservation.getId());
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
    }

    public static void printReservationTheme(Theme theme) {
        System.out.println("예약 테마 이름: " + theme.getName());
        System.out.println("예약 테마 설명: " + theme.getDesc());
        System.out.println("예약 테마 가격: " + theme.getPrice());
    }
}

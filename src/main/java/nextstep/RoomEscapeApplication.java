package nextstep;

import nextstep.dao.ReservationDAO;
import nextstep.dao.DAOImple.SimpleReservationDAO;
import nextstep.dao.DAOImple.SimpleThemeDAO;
import nextstep.dao.ThemeDAO;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeRepository;
import nextstep.repository.repositoryImpl.ReservationRepositoryImpl;
import nextstep.repository.repositoryImpl.ThemeRepositoryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String ADD_THEME = "theme add";
    private static final String FIND_THEME = "theme find";
    private static final String DELETE_THEME = "theme delete";
    private static final String QUIT = "quit";

    private static void initDB() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");

        Connection connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");

        final String RESERVATION_SQL = "CREATE TABLE RESERVATION" +
                "(" +
                "    id       bigint not null auto_increment," +
                "    date     date," +
                "    time     time," +
                "    name     varchar(20)," +
                "    theme_id bigint not null," +
                "    primary key (id)" +
                ")";
        final String THEME_SQL = "CREATE TABLE theme" +
                "(" +
                "    id    bigint not null auto_increment," +
                "    name  varchar(20)," +
                "    desc  varchar(255)," +
                "    price int," +
                "    primary key (id)" +
                ")";

        PreparedStatement psReservation = connection.prepareStatement(RESERVATION_SQL);
        PreparedStatement psTheme = connection.prepareStatement(THEME_SQL);

        psReservation.execute();
        psTheme.execute();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        initDB();

        Scanner scanner = new Scanner(System.in);

        ReservationDAO reservationDAO = new SimpleReservationDAO();
        ReservationRepository reservationRepository = new ReservationRepositoryImpl(reservationDAO);
        ThemeDAO themeDAO = new SimpleThemeDAO();
        ThemeRepository themeRepository = new ThemeRepositoryImpl(themeDAO);

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add {date},{time},{name},{theme id} ex) add 2022-08-11,13:00,류성현,1");
            System.out.println("- 예약조회: find {id} ex) find 1");
            System.out.println("- 예약취소: delete {id} ex) delete 1");
            System.out.println("- 테마생성: theme add {name},{desc},{price} ex) theme add name,desc,10000");
            System.out.println("- 테마조회: theme find");
            System.out.println("- 테마삭제: theme delete {id} ex) theme delete 1");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];
                Long theme_id = Long.parseLong(params.split(",")[3]);

                LocalDate localDate = LocalDate.parse(date);
                LocalTime localTime = LocalTime.parse(time + ":00");

                if (reservationRepository.existsByDateTime(localDate, localTime)) {
                    System.out.println("해당 시간에 이미 예약이 존재합니다");
                    continue;
                }

                Theme theme = themeRepository.getById(theme_id);

                if (theme == null) {
                    System.out.println("존재하지 않는 테마입니다.");
                    continue;
                }

                Reservation reservation = new Reservation(
                        null,
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name,
                        theme_id
                );
                Long id = reservationRepository.insert(reservation);
                reservation = reservationRepository.getById(id);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 번호: " + reservation.getTheme_id());
                System.out.println("예약 테마 이름: " + theme.getName());
                System.out.println("예약 테마 설명: " + theme.getDesc());
                System.out.println("예약 테마 가격: " + theme.getPrice());
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation = reservationRepository.getById(id);
                if (reservation == null) {
                    System.out.println("잘못된 예약 번호입니다.");
                    continue;
                }

                Theme theme = themeRepository.getById(reservation.getTheme_id());

                if (reservation != null) {
                    System.out.println("예약 번호: " + reservation.getId());
                    System.out.println("예약 날짜: " + reservation.getDate());
                    System.out.println("예약 시간: " + reservation.getTime());
                    System.out.println("예약자 이름: " + reservation.getName());
                    System.out.println("예약 테마 번호: " + reservation.getTheme_id());
                    System.out.println("예약 테마 이름: " + theme.getName());
                    System.out.println("예약 테마 설명: " + theme.getDesc());
                    System.out.println("예약 테마 가격: " + theme.getPrice());
                }
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                if (reservationRepository.deleteById(id)) {
                    System.out.println("예약이 취소되었습니다.");
                }
            }

            if (input.startsWith(ADD_THEME)) {
                String params = input.split(" ")[2];

                String name = params.split(",")[0];
                String desc = params.split(",")[1];
                int price = Integer.parseInt(params.split(",")[2]);

                Theme theme = new Theme(name, desc, price);

                Long id = themeRepository.insert(theme);

                theme = themeRepository.getById(id);

                System.out.println("테마가 등록되었습니다.");
                System.out.println("테마 번호: " + id);
                System.out.println("테마 이름: " + theme.getName());
                System.out.println("테마 설명: " + theme.getDesc());
                System.out.println("테마 가격: " + theme.getPrice());
            }

            if (input.startsWith(FIND_THEME)) {
                List<Theme> themeList = themeRepository.getList();

                if (!themeList.isEmpty()) {
                    for (Theme theme : themeList) {
                        System.out.println("테마 번호: " + theme.getId());
                        System.out.println("테마 이름: " + theme.getName());
                        System.out.println("테마 설명: " + theme.getDesc());
                        System.out.println("테마 가격: " + theme.getPrice() + "\n");
                    }
                }
            }

            if (input.startsWith(DELETE_THEME)) {
                String params = input.split(" ")[2];

                Long id = Long.parseLong(params.split(",")[0]);

                if (themeRepository.deleteById(id)) {
                    System.out.println("테마가 삭제되었습니다.");
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

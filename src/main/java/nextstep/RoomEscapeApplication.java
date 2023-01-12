package nextstep;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import nextstep.dto.ConnectionHandler;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;
import nextstep.entity.Reservation;
import nextstep.repository.ReservationJdbcRepositoryImpl;
import nextstep.repository.ReservationRepository;
import nextstep.service.ReservationService;
import nextstep.service.ReservationServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    private static final ConnectionHandler connectionHandler = new ConnectionHandler();
    private static final ReservationRepository reservationJDBCRepository = new ReservationJdbcRepositoryImpl(
            connectionHandler);
    private static final ReservationService reservationService = new ReservationServiceImpl(reservationJDBCRepository);

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(RoomEscapeApplication.class, args);
        Scanner scanner = new Scanner(System.in);

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
                Reservation reservation = reservationService.createReservation(
                        new ReservationRequestDTO(LocalDate.parse(date), LocalTime.parse(time + ":00"), name));

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);
                ReservationResponseDTO reservationResponseDTO = reservationService.findReservation(id);

                System.out.println("예약 번호: " + reservationResponseDTO.getId());
                System.out.println("예약 날짜: " + reservationResponseDTO.getDate());
                System.out.println("예약 시간: " + reservationResponseDTO.getTime());
                System.out.println("예약자 이름: " + reservationResponseDTO.getName());
                System.out.println("예약 테마 이름: " + reservationResponseDTO.getThemeName());
                System.out.println("예약 테마 설명: " + reservationResponseDTO.getThemeDescription());
                System.out.println("예약 테마 가격: " + reservationResponseDTO.getThemePrice());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);

                reservationService.deleteById(id);

            }

            if (input.equals(QUIT)) {
                connectionHandler.release();
                break;
            }
        }
    }
}

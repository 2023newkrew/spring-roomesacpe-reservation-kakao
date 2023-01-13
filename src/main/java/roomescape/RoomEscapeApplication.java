package roomescape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import roomescape.domain.Reservation;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.ReservationConsoleRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

@SpringBootApplication
public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    private static ReservationConsoleRepository reservationConsoleRepository;
    private final ReservationConsoleRepository repository;

    @PostConstruct
    void init(){
        reservationConsoleRepository = repository;
    }

    public RoomEscapeApplication(ReservationConsoleRepository repository){
        this.repository = repository;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SpringApplication.run(RoomEscapeApplication.class, args);

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add {date},{time},{name},{theme_id} ex) add 2022-08-11,13:00,류성현,1");
            System.out.println("- 예약조회: find {id} ex) find 1");
            System.out.println("- 예약취소: delete {id} ex) delete 1");
            System.out.println("- 종료: quit");


            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                String params = input.split(" ")[1];

                LocalDate date = LocalDate.parse(params.split(",")[0]);
                LocalTime time = LocalTime.parse(params.split(",")[1] + ":00");
                String name = params.split(",")[2];
                Long theme_id = Long.valueOf(params.split(",")[3]);

                Reservation reservation;

                try {
                    reservation = new Reservation(null, date, time, name, theme_id);
                    reservationConsoleRepository.findReservationByDateAndTime(date, time)
                            .ifPresent((e) -> {
                                throw new RoomEscapeException(ErrorCode.DUPLICATED_RESERVATION);
                            });
                } catch (RoomEscapeException e) {
                    System.err.println(e.getMessage());
                    continue;
                }

                reservationConsoleRepository.save(reservation);

                reservation = reservationConsoleRepository.findReservationByDateAndTime(date, time)
                        .orElseThrow(() -> new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND));
                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);
                Reservation reservation;
                try {
                    reservation = reservationConsoleRepository.findOne(id)
                            .orElseThrow(() -> {
                                throw new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND);
                            });
                } catch (RoomEscapeException e) {
                    System.err.println(e.getMessage());
                    continue;
                }

                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
//                System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
//                System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
//                System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                if (reservationConsoleRepository.findOne(id).isPresent()) {
                    reservationConsoleRepository.delete(id);
                    System.out.println("예약이 취소되었습니다.");
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

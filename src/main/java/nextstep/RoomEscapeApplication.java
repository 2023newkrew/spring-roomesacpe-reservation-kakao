package nextstep;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Scanner;
import kakao.dto.request.CreateReservationRequest;
import kakao.dto.response.ReservationResponse;
import kakao.service.ReservationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RoomEscapeApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ConsoleConfig.class);

        ReservationService reservationService = ctx.getBean(ReservationService.class);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add {date},{time},{name},{themeId} ex) add 2022-08-11,13:00,류성현,1");
            System.out.println("- 예약조회: find {id} ex) find 1");
            System.out.println("- 예약취소: delete {id} ex) delete 1");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(ADD)) {
                String[] params = input.split(" ")[1].split(",");

                CreateReservationRequest request = new CreateReservationRequest(
                        LocalDate.parse(params[0]),
                        LocalTime.parse(params[1]),
                        params[2],
                        Long.parseLong(params[3])
                );

                ReservationResponse response = reservationService.createReservation(request);

                if (Objects.isNull(response)) {
                    System.err.println("예약이 불가능합니다.");
                }
                    System.out.println("예약이 등록되었습니다.");
                    System.out.println("예약 번호: " + response.id);
                    System.out.println("예약 날짜: " + response.date);
                    System.out.println("예약 시간: " + response.time);
                    System.out.println("예약자 이름: " + response.name);
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                ReservationResponse response = reservationService.getReservation(id);
                if (Objects.isNull(response)) {
                    throw new RuntimeException("해당 ID의 예약이 존재하지 않습니다.");
                }

                System.out.println("예약 번호: " + response.id);
                System.out.println("예약 날짜: " + response.date);
                System.out.println("예약 시간: " + response.time);
                System.out.println("예약자 이름: " + response.name);
                System.out.println("예약 테마 이름: " + response.theme.name);
                System.out.println("예약 테마 설명: " + response.theme.desc);
                System.out.println("예약 테마 가격: " + response.theme.price);
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                if (reservationService.deleteReservation(id) > 0) {
                    System.out.println("예약이 취소되었습니다.");
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

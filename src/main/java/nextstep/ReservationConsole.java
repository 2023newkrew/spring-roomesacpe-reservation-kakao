package nextstep;

import nextstep.dto.ReservationRequest;
import nextstep.dto.ReservationResponse;
import nextstep.exceptions.exception.InvalidRequestException;
import nextstep.repository.ReservationJdbcDao;
import nextstep.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class ReservationConsole {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";


    public static void main(String[] args) {
        final ReservationService reservationService = new ReservationService(new ReservationJdbcDao());
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

                ReservationRequest reservationRequest = new ReservationRequest(
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name
                );

                try {
                    Long id = reservationService.reserve(reservationRequest);
                    System.out.println("예약이 등록되었습니다.");
                    System.out.println("예약 번호: " + id);
                    System.out.println("예약 날짜: " + reservationRequest.getDate());
                    System.out.println("예약 시간: " + reservationRequest.getTime());
                    System.out.println("예약자 이름: " + reservationRequest.getName());
                } catch (InvalidRequestException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                try {
                    ReservationResponse reservationDto = reservationService.retrieve(id);
                    System.out.println("예약 번호: " + reservationDto.getId());
                    System.out.println("예약 날짜: " + reservationDto.getDate());
                    System.out.println("예약 시간: " + reservationDto.getTime());
                    System.out.println("예약자 이름: " + reservationDto.getName());
                    System.out.println("예약 테마 이름: " + reservationDto.getThemeName());
                    System.out.println("예약 테마 설명: " + reservationDto.getThemeDesc());
                    System.out.println("예약 테마 가격: " + reservationDto.getThemePrice());
                } catch (InvalidRequestException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                try {
                    reservationService.delete(id);
                    System.out.println("예약이 취소되었습니다.");
                } catch (InvalidRequestException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

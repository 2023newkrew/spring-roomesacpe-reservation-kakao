package nextstep;

import nextstep.domain.Theme;
import nextstep.dto.ReservationRequestDto;
import nextstep.dto.ReservationResponseDto;
import nextstep.exceptions.exception.DuplicatedDateAndTimeException;
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

                ReservationRequestDto reservationRequestDto = new ReservationRequestDto(
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name
                );

                try {
                    Long id = reservationService.reserve(reservationRequestDto);
                    System.out.println("예약이 등록되었습니다.");
                    System.out.println("예약 번호: " + id);
                    System.out.println("예약 날짜: " + reservationRequestDto.getDate());
                    System.out.println("예약 시간: " + reservationRequestDto.getTime());
                    System.out.println("예약자 이름: " + reservationRequestDto.getName());
                } catch (DuplicatedDateAndTimeException e) {
                    System.out.println("이미 예약된 날짜와 시간입니다.");
                }
            }


            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                ReservationResponseDto reservationDto = reservationService.retrieve(id);
                if (reservationDto != null) {
                    System.out.println("예약 번호: " + reservationDto.getId());
                    System.out.println("예약 날짜: " + reservationDto.getDate());
                    System.out.println("예약 시간: " + reservationDto.getTime());
                    System.out.println("예약자 이름: " + reservationDto.getName());
                    System.out.println("예약 테마 이름: " + reservationDto.getThemeName());
                    System.out.println("예약 테마 설명: " + reservationDto.getThemeDesc());
                    System.out.println("예약 테마 가격: " + reservationDto.getThemePrice());
                }
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];

                Long id = Long.parseLong(params.split(",")[0]);

                reservationService.delete(id);
                System.out.println("예약이 취소되었습니다.");
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

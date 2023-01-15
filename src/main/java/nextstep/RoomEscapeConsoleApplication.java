package nextstep;

import nextstep.reservation.dto.request.ReservationRequestDto;
import nextstep.reservation.dto.response.ReservationResponseDto;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.repository.reservation.ReservationTraditionalRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import nextstep.reservation.repository.theme.ThemeTraditionalRepository;
import nextstep.reservation.service.ReservationService;


public class RoomEscapeConsoleApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    private static final String URL = "jdbc:h2:~/workspace/kakao/spring-roomesacpe-reservation-kakao/room-escape";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservationService reservationService = new ReservationService(
                new ReservationTraditionalRepository(URL, USER, PASSWORD),
                new ThemeTraditionalRepository(URL, USER, PASSWORD) // Todo : Config 깔끔하게 설정 할 방법 찾기
        );
        long reservationIdIndex = 0L;

        Theme theme = new Theme(1L, "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

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

                Reservation reservation = Reservation.builder()
                        .id(++reservationIdIndex)
                        .date(LocalDate.parse(date))
                        .time(LocalTime.parse(time + ":00"))
                        .name(name)
                        .theme(theme)
                        .build();
                ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
                        .date(LocalDate.parse(date))
                        .time(LocalTime.parse(time + ":00"))
                        .name(name)
                        .build();
                reservationService.addReservation(reservationRequestDto);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
            }

            if (input.startsWith(FIND)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);
                ReservationResponseDto reservationResponseDto = reservationService.getReservation(id);
                System.out.println("예약 번호: " + reservationResponseDto.getId());
                System.out.println("예약 날짜: " + reservationResponseDto.getDate());
                System.out.println("예약 시간: " + reservationResponseDto.getTime());
                System.out.println("예약자 이름: " + reservationResponseDto.getName());
                System.out.println("예약 테마 이름: " + reservationResponseDto.getTheme().getName());
                System.out.println("예약 테마 설명: " + reservationResponseDto.getTheme().getDesc());
                System.out.println("예약 테마 가격: " + reservationResponseDto.getTheme().getPrice());
            }

            if (input.startsWith(DELETE)) {
                String params = input.split(" ")[1];
                Long id = Long.parseLong(params.split(",")[0]);
                reservationService.deleteReservation(id);
                System.out.println("예약이 취소되었습니다.");
            }

            if (input.equals(QUIT)) {
                break;
            }
            if (input.equals("all")){
                System.out.println(reservationService.getAllReservation());
            }
        }
    }
}

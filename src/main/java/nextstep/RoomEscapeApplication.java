package nextstep;

import roomescape.dto.*;
import roomescape.repository.ReservationConsoleRepository;
import roomescape.repository.ThemeConsoleRepository;
import roomescape.service.ReservationService;
import roomescape.service.ThemeService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String ADD_R = "add res";
    private static final String FIND_R = "find res";
    private static final String DELETE_R = "delete res";
    private static final String ADD_T = "add theme";
    private static final String UPDATE_T = "update theme";
    private static final String FIND_T = "find theme";
    private static final String FINDALL_T = "findall theme";
    private static final String DELETE_T = "delete theme";
    private static final String QUIT = "quit";

    public static void main(String[] args) {
        ReservationConsoleRepository reservationConsoleRepository = new ReservationConsoleRepository();
        ThemeConsoleRepository themeConsoleRepository = new ThemeConsoleRepository();
        ReservationService reservationService = new ReservationService(reservationConsoleRepository, themeConsoleRepository);
        ThemeService themeService = new ThemeService(themeConsoleRepository, reservationConsoleRepository);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add res {date},{time},{name},{theme_id} ex) add res 2022-08-11,13:00,류성현,1");
            System.out.println("- 예약조회: find res {id} ex) find res 1");
            System.out.println("- 예약취소: delete res {id} ex) delete res 1");
            System.out.println("- 테마추가: add theme {name},{desc},{price} ex) add theme 테마테마,LoremIpsum,1000");
            System.out.println("- 테마수정: update theme {theme_id},name={name},desc={desc},price={price} ex) update theme 1,name=테마테마,price=1000");
            System.out.println("- 테마조회: find theme {id} ex) find theme 1");
            System.out.println("- 모든테마조회: findall theme ex) findall theme");
            System.out.println("- 테마삭제: delete theme {id} ex) delete theme 1");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(ADD_R)) {
                String params = input.split(" ")[2];

                LocalDate date = LocalDate.parse(params.split(",")[0]);
                LocalTime time = LocalTime.parse(params.split(",")[1] + ":00");
                String name = params.split(",")[2];
                Long themeId = Long.parseLong(params.split(",")[3]);

                ReservationRequestDto reservationRequestDto = new ReservationRequestDto(date, time, name, themeId);

                Long reservationId = reservationService.createReservation(reservationRequestDto);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservationId);
                System.out.println("예약 날짜: " + reservationRequestDto.getDate());
                System.out.println("예약 시간: " + reservationRequestDto.getTime());
                System.out.println("예약자 이름: " + reservationRequestDto.getName());
            }

            if (input.startsWith(FIND_R)) {
                String params = input.split(" ")[2];

                Long id = Long.parseLong(params.split(",")[0]);

                ReservationResponseDto reservationResponseDto = reservationService.findReservation(id);

                System.out.println("예약 번호: " + reservationResponseDto.getId());
                System.out.println("예약 날짜: " + reservationResponseDto.getDate());
                System.out.println("예약 시간: " + reservationResponseDto.getTime());
                System.out.println("예약자 이름: " + reservationResponseDto.getName());
                System.out.println("예약 테마 이름: " + reservationResponseDto.getThemeName());
                System.out.println("예약 테마 설명: " + reservationResponseDto.getThemeDesc());
                System.out.println("예약 테마 가격: " + reservationResponseDto.getThemePrice());
            }

            if (input.startsWith(DELETE_R)) {
                String params = input.split(" ")[2];

                Long id = Long.parseLong(params.split(",")[0]);

                reservationService.deleteReservation(id);
                System.out.println("예약이 취소되었습니다.");
            }

            if (input.startsWith(ADD_T)) {
                String params = input.split(" ")[2];

                String name = params.split(",")[0];
                String desc = params.split(",")[1];
                Integer price = Integer.parseInt(params.split(",")[2]);

                ThemeRequestDto themeRequestDto = new ThemeRequestDto(name, desc, price);

                Long themeId = themeService.createTheme(themeRequestDto);

                System.out.println("테마가 등록되었습니다.");
                System.out.println("테마 번호: " + themeId);
                System.out.println("테마 이름: " + themeRequestDto.getName());
                System.out.println("테마 설명: " + themeRequestDto.getDesc());
                System.out.println("테마 가격: " + themeRequestDto.getPrice());
            }

            if (input.startsWith(UPDATE_T)) {
                String params = input.split(" ")[2];

                String[] splitUserInput = params.split(",");
                long themeId = Long.parseLong(splitUserInput[0]);
                String name = null;
                String desc = null;
                Integer price = null;

                for (String keyValue : Arrays.copyOfRange(splitUserInput, 1, splitUserInput.length) ) {
                    String key = keyValue.split("=")[0];
                    String value = keyValue.split("=")[1];
                    if (key.equals("name")) name = value;
                    if (key.equals("desc")) desc = value;
                    if (key.equals("price")) price = Integer.parseInt(value);
                }

                ThemeUpdateRequestDto themeUpdateRequestDto = new ThemeUpdateRequestDto(name, desc, price);
                themeService.updateTheme(themeId, themeUpdateRequestDto);

                System.out.println("테마가 수정되었습니다.");
                System.out.println("테마 번호: " + themeId);
            }

            if (input.startsWith(FIND_T)) {
                String params = input.split(" ")[2];

                Long id = Long.parseLong(params.split(",")[0]);

                ThemeResponseDto themeResponseDto = themeService.findTheme(id);

                System.out.println("테마 번호: " + themeResponseDto.getId());
                System.out.println("테마 이름: " + themeResponseDto.getName());
                System.out.println("테마 설명: " + themeResponseDto.getDesc());
                System.out.println("테마 가격: " + themeResponseDto.getPrice());
            }

            if (input.startsWith(FINDALL_T)) {
                List<ThemeResponseDto> themeResponseDtos = themeService.findAllTheme().getThemes();

                for (ThemeResponseDto themeResponseDto : themeResponseDtos) {
                    System.out.println("\n테마 번호: " + themeResponseDto.getId());
                    System.out.println("테마 이름: " + themeResponseDto.getName());
                    System.out.println("테마 설명: " + themeResponseDto.getDesc());
                    System.out.println("테마 가격: " + themeResponseDto.getPrice());
                }
            }

            if (input.startsWith(DELETE_T)) {
                String params = input.split(" ")[2];

                Long id = Long.parseLong(params.split(",")[0]);

                themeService.deleteTheme(id);
                System.out.println("테마가 삭제되었습니다.");
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}

package nextstep.console.view;

import java.util.Arrays;
import nextstep.dto.ReservationResponseDTO;
import nextstep.dto.ThemeResponseDto;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;

public class OutputView {

    public void printCommand(){
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: add {date},{time},{name},{theme_id} ex) add 2022-08-11,13:00,류성현,1");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
        System.out.println("- 테마생성: theme add {name},{desc},{price} ex) theme add 테마이름,설명,30000");
        System.out.println("- 테마조회: theme find {id} ex) theme find 1");
        System.out.println("- 테마삭제: theme remove {id} ex) theme remove 1");
        System.out.println("- 테마수정: theme edit {id},{name},{desc},{price} theme edit 1,테마이름2,설명2,40000");
        System.out.println("- 종료: quit");
    }

    public void printReservation(Reservation reservation) {
        System.out.println("예약이 등록되었습니다.");
        System.out.println("예약 번호: " + reservation.getId());
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
        System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
        System.out.println("예약 테마 설명: " + reservation.getTheme().getDescription());
        System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
    }

    public void printReservationResponseDto(ReservationResponseDTO reservationResponseDTO) {
        System.out.println("예약 번호: " + reservationResponseDTO.getId());
        System.out.println("예약 날짜: " + reservationResponseDTO.getDate());
        System.out.println("예약 시간: " + reservationResponseDTO.getTime());
        System.out.println("예약자 이름: " + reservationResponseDTO.getName());
        System.out.println("예약 테마 이름: " + reservationResponseDTO.getThemeName());
        System.out.println("예약 테마 설명: " + reservationResponseDTO.getThemeDescription());
        System.out.println("예약 테마 가격: " + reservationResponseDTO.getThemePrice());
    }

    public void printThemeResponseDto(ThemeResponseDto themeResponseDto) {
        System.out.println("테마 번호: " + themeResponseDto.getId());
        System.out.println("테마 이름: " + themeResponseDto.getName());
        System.out.println("테마 설명: " + themeResponseDto.getDescription());
        System.out.println("테마 가격: " + themeResponseDto.getPrice());
    }

    public void printTheme(Theme theme) {
        System.out.println("테마 번호: " + theme.getId());
        System.out.println("테마 이름: " + theme.getName());
        System.out.println("테마 설명: " + theme.getDescription());
        System.out.println("테마 가격: " + theme.getPrice());
    }

    public void printErrorMessage(Exception e, String[] messages) {
        System.out.println(e.getMessage());
        Arrays.stream(messages).forEach(System.out::println);
    }
}

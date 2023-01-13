package nextstep.console.view;

import nextstep.dto.ReservationResponseDTO;
import nextstep.entity.Reservation;

public class OutputView {

    public void printCommand(){
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: add {date},{time},{name},{theme_id} ex) add 2022-08-11,13:00,류성현,1");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
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
}

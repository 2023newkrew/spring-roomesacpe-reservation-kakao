package roomescape.view;

import org.springframework.stereotype.Component;
import roomescape.dto.ReservationResponseDto;

import java.util.Optional;
import java.util.Scanner;

@Component("consoleView")
public class ConsoleView {

    private Scanner scanner;

    public ConsoleView() {
        scanner = new Scanner(System.in);
    }

    public String inputCommand() {
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: add {date},{time},{name},{theme_id} ex) add 2022-08-11,13:00,류성현,1");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
        System.out.println("- 종료: quit");
        return scanner.nextLine();
    }

    public void showCreatedReservation(Optional<ReservationResponseDto> optionalRes) {
        if (optionalRes.isEmpty()) {
            System.out.println("예약 생성에 실패하였습니다.");
            return;
        }
        ReservationResponseDto res = optionalRes.get();
        System.out.println("예약이 등록되었습니다.");
        System.out.println("예약 번호: " + res.getId());
        System.out.println("예약 날짜: " + res.getDate());
        System.out.println("예약 시간: " + res.getTime());
        System.out.println("예약자 이름: " + res.getName());
    }

    public void showFoundReservation(Optional<ReservationResponseDto> optionalRes) {
        if (optionalRes.isEmpty()) {
            System.out.println("예약 조회에 실패하였습니다.");
            return;
        }
        ReservationResponseDto res = optionalRes.get();
        System.out.println("예약 번호: " + res.getId());
        System.out.println("예약 날짜: " + res.getDate());
        System.out.println("예약 시간: " + res.getTime());
        System.out.println("예약자 이름: " + res.getName());
        System.out.println("예약 테마 이름: " + res.getThemeName());
        System.out.println("예약 테마 설명: " + res.getThemeDesc());
        System.out.println("예약 테마 가격: " + res.getThemePrice());
    }

    public void showCanceledCoReservationCount(Optional<Boolean> optionalRes) {
        if (optionalRes.isEmpty()) {
            System.out.println("예약 취소에 실패하였습니다.");
            return;
        }
        Boolean success = optionalRes.get();
        if (success) {
            System.out.println("예약이 취소되었습니다.");
        } else {
            System.out.println("예약 취소에 실패하였습니다.");
        }
    }

    public void showInvalidInput() {
        System.out.println("잘못된 입력입니다.");
    }

    public void close() {
        scanner.close();
    }
}

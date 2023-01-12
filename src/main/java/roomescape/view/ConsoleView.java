package roomescape.view;

import org.springframework.stereotype.Component;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ThemeResponseDto;
import roomescape.dto.ThemesResponseDto;
import roomescape.model.Theme;

import java.util.List;
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
        System.out.println("- 예약하기: add reservation {date},{time},{name},{theme_id} ex) add reservation 2022-08-11,13:00,류성현,1");
        System.out.println("- 예약조회: find reservation {id} ex) find reservation 1");
        System.out.println("- 예약취소: delete reservation {id} ex) delete reservation 1");
        System.out.println("- 테마생성: add theme {name},{desc},{price} ex) add theme 워너고홈,병맛 어드벤처 회사 코믹물,29000");
        System.out.println("- 테마보기: show theme");
        System.out.println("- 테마삭제: delete theme {id} ex) delete theme 1");
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

    public void showIsReservationCanceled(Boolean success) {
        if (success) {
            System.out.println("예약이 취소되었습니다.");
        } else {
            System.out.println("예약 취소에 실패하였습니다.");
        }
    }

    public void showCreatedTheme(Optional<ThemeResponseDto> optionalRes) {
        if (optionalRes.isEmpty()) {
            System.out.println("테마 생성에 실패하였습니다.");
            return;
        }
        ThemeResponseDto res = optionalRes.get();
        System.out.println("테마가 생성되었습니다.");
        System.out.println("테마 번호: " + res.getId());
        System.out.println("테마 이름: " + res.getName());
        System.out.println("테마 설명: " + res.getDesc());
        System.out.println("테마 가격: " + res.getPrice());
    }

    public void showThemes(Optional<ThemesResponseDto> optionalRes) {
        if (optionalRes.isEmpty()) {
            System.out.println("테마 조회에 실패하였습니다.");
            return;
        }
        ThemesResponseDto res = optionalRes.get();
        List<Theme> themes = res.getThemes();
        for (Theme theme : themes) {
            System.out.println();
            System.out.println("테마 번호: " + theme.getId());
            System.out.println("테마 이름: " + theme.getName());
            System.out.println("테마 설명: " + theme.getDesc());
            System.out.println("테마 가격: " + theme.getPrice());
        }
    }

    public void showIsThemeDeleted(Boolean success) {
        if (success) {
            System.out.println("테마가 삭제되었습니다.");
        } else {
            System.out.println("테마 삭제에 실패하였습니다.");
        }
    }

    public void showInvalidInput() {
        System.out.println("잘못된 입력입니다.");
    }

    public void close() {
        scanner.close();
    }
}

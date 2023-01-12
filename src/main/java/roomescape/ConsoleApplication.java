package roomescape;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import roomescape.controller.ConsoleController;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ThemeResponseDto;
import roomescape.dto.ThemesResponseDto;
import roomescape.view.ConsoleView;

import java.util.Optional;

@Component("consoleApplication")
public class ConsoleApplication {
    private static final String ADD_RESERVATION = "add reservation";
    private static final String FIND_RESERVATION = "find reservation";
    private static final String DELETE_RESERVATION = "delete reservation";
    private static final String ADD_THEME = "add theme";
    private static final String SHOW_THEME = "show theme";
    private static final String DELETE_THEME = "delete theme";
    private static final String QUIT = "quit";
    private final ConsoleController consoleController;
    private final ConsoleView consoleView;

    public ConsoleApplication(ConsoleController consoleController, ConsoleView consoleView) {
        this.consoleController = consoleController;
        this.consoleView = consoleView;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        while (true) {
            String input = consoleView.inputCommand();
            // 예약하기
            if (input.startsWith(ADD_RESERVATION)) {
                Optional<ReservationResponseDto> res = consoleController.createReservation(input);
                consoleView.showCreatedReservation(res);
            }
            // 예약 조회
            else if (input.startsWith(FIND_RESERVATION)) {
                Optional<ReservationResponseDto> res = consoleController.findReservation(input);
                consoleView.showFoundReservation(res);
            }
            // 예약 취소
            else if (input.startsWith(DELETE_RESERVATION)) {
                Boolean res = consoleController.cancelReservation(input);
                consoleView.showIsReservationCanceled(res);
            }
            // 테마 생성
            else if (input.startsWith(ADD_THEME)) {
                Optional<ThemeResponseDto> res = consoleController.createTheme(input);
                consoleView.showCreatedTheme(res);
            }
            // 테마 리스트 조회
            else if (input.startsWith(SHOW_THEME)) {
                Optional<ThemesResponseDto> res = consoleController.findThemes();
                consoleView.showThemes(res);
            }
            // 테마 삭제
            else if (input.startsWith(DELETE_THEME)) {
                Boolean res = consoleController.deleteTheme(input);
                consoleView.showIsThemeDeleted(res);
            }
            // 종료
            else if (input.equals(QUIT)) {
                consoleView.close();
                break;
            }
            // 잘못된 입력
            else {
                consoleView.showInvalidInput();
            }
        }
    }
}

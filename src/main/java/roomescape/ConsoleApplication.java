package roomescape;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import roomescape.controller.ConsoleController;

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

    public ConsoleApplication(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        while (true) {
            String input = consoleController.getCommand();
            // 예약하기
            if (input.startsWith(ADD_RESERVATION)) {
                consoleController.createReservation(input);
            }
            // 예약 조회
            else if (input.startsWith(FIND_RESERVATION)) {
                consoleController.findReservation(input);
            }
            // 예약 취소
            else if (input.startsWith(DELETE_RESERVATION)) {
                consoleController.cancelReservation(input);
            }
            // 테마 생성
            else if (input.startsWith(ADD_THEME)) {
                consoleController.createTheme(input);
            }
            // 테마 리스트 조회
            else if (input.startsWith(SHOW_THEME)) {
                consoleController.findThemes();
            }
            // 테마 삭제
            else if (input.startsWith(DELETE_THEME)) {
                consoleController.deleteTheme(input);
            }
            // 종료
            else if (input.equals(QUIT)) {
                consoleController.close();
                break;
            }
            // 잘못된 입력
            else {
                consoleController.receivedInvalidInput();
            }
        }
    }
}

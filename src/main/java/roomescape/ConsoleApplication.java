package roomescape;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import roomescape.controller.ConsoleController;
import roomescape.dto.ReservationResponseDto;
import roomescape.view.ConsoleView;

import java.util.Optional;

@Component("consoleApplication")
public class ConsoleApplication {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
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
            if (input.startsWith(ADD)) {
                Optional<ReservationResponseDto> res = consoleController.createReservation(input);
                consoleView.showCreatedReservation(res);
            }
            // 조회하기
            else if (input.startsWith(FIND)) {
                Optional<ReservationResponseDto> res = consoleController.findReservation(input);
                consoleView.showFoundReservation(res);
            }
            // 예약취소
            else if (input.startsWith(DELETE)) {
                Optional<Boolean> res = consoleController.cancelReservation(input);
                consoleView.showCanceledCoReservationCount(res);
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

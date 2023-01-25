package nextstep;

import nextstep.console.InputHandler;
import nextstep.console.Printer;
import nextstep.console.ReservationInputHandler;
import nextstep.console.ThemeInputHandler;
import nextstep.repository.ReservationH2Repository;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeH2Repository;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String QUIT = "quit";
    private static final List<InputHandler> inputHandlers = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        setInputHandlers();

        while (true) {
            Printer.printGuideMessage();
            String input = scanner.nextLine();
            if (input.equals(QUIT)) {
                break;
            }
            handleInput(input);
        }
    }

    private static void setInputHandlers() {
        ReservationRepository reservationRepository = new ReservationH2Repository();
        ThemeH2Repository themeRepository = new ThemeH2Repository();
        ReservationService reservationService = new ReservationService(reservationRepository, themeRepository);
        ThemeService themeService = new ThemeService(themeRepository, reservationRepository);
        inputHandlers.add(new ReservationInputHandler(reservationService));
        inputHandlers.add(new ThemeInputHandler(themeService));
    }

    public static void handleInput(String input) {
        for (InputHandler inputHandler : inputHandlers) {
            if (inputHandler.supports(input)) {
                inputHandler.handle(input);
                return;
            }
        }
        Printer.printInvalidInputErrorMessage();
    }
}

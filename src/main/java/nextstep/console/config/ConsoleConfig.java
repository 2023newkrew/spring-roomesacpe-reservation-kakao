package nextstep.console.config;

import nextstep.console.controller.ReservationConsoleController;
import nextstep.console.controller.ThemeConsoleController;
import nextstep.console.utils.ConnectionHandler;
import nextstep.console.view.InputView;
import nextstep.console.view.OutputView;
import nextstep.console.view.View;
import nextstep.repository.ReservationJdbcRepositoryImpl;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeJdbcRepositoryImpl;
import nextstep.repository.ThemeRepository;
import nextstep.service.ReservationServiceImpl;
import nextstep.service.ThemeServiceImpl;

public class ConsoleConfig {

    private static ConsoleConfig INSTANCE = null;
    private final ReservationConsoleController reservationConsoleController;
    private final ThemeConsoleController themeConsoleController;
    private final View view;


    private ConsoleConfig() {
        // connection handler
        ConnectionHandler connectionHandler = new ConnectionHandler();

        // view
        this.view = new View(new InputView(), new OutputView());

        // Repository
        ReservationRepository reservationRepository = new ReservationJdbcRepositoryImpl(connectionHandler);
        ThemeRepository themeRepository = new ThemeJdbcRepositoryImpl(connectionHandler);

        // service
        ReservationServiceImpl reservationService = new ReservationServiceImpl(reservationRepository);
        ThemeServiceImpl themeService = new ThemeServiceImpl(themeRepository);

        // controller
        this.reservationConsoleController = new ReservationConsoleController(reservationService, themeService, view);
        this.themeConsoleController = new ThemeConsoleController(themeService, reservationService, view);

    }

    public static ConsoleConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConsoleConfig();
        }
        return INSTANCE;
    }

    public ReservationConsoleController getReservationConsoleController() {
        return reservationConsoleController;
    }

    public ThemeConsoleController getThemeConsoleController() {
        return themeConsoleController;
    }

    public View getView() {
        return view;
    }
}

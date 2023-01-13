package nextstep.console;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.console.view.View;
import nextstep.dto.ConnectionHandler;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ThemeResponseDto;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import nextstep.repository.ReservationJdbcRepositoryImpl;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeJdbcRepositoryImpl;
import nextstep.repository.ThemeRepository;
import nextstep.service.ReservationService;
import nextstep.service.ReservationServiceImpl;
import nextstep.service.ThemeService;
import nextstep.service.ThemeServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApp {

    private static final ConnectionHandler connectionHandler = new ConnectionHandler();

    // reservation
    private static final ReservationRepository reservationJDBCRepository = new ReservationJdbcRepositoryImpl(
            connectionHandler);
    private static final ReservationService reservationService = new ReservationServiceImpl(reservationJDBCRepository);

    // theme
    private static final ThemeRepository themeRepository = new ThemeJdbcRepositoryImpl(connectionHandler);
    private static final ThemeService themeService = new ThemeServiceImpl(themeRepository);


    private static final View view = new View();
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    public static void run() throws SQLException {
        while (true) {
            view.printCommand();
            String input = view.readLine();
            if (input.startsWith(ADD)) {
                view.printReservation(makeReservation(input));
            }
            if (input.startsWith(FIND)) {
                Long id = parseId(input);
                view.printReservationResponseDto(reservationService.findReservation(id));
            }
            if (input.startsWith(DELETE)) {
                Long id = parseId(input);
                reservationService.deleteById(id);
            }
            if (input.equals(QUIT)) {
                connectionHandler.release();
                System.exit(0);
                break;
            }
        }
    }

    private static Long parseId(String input) {
        String params = parseParams(input);
        return Long.parseLong(params.split(",")[0]);
    }

    private static String parseParams(String input) {
        return input.split(" ")[1];
    }


    private static Reservation makeReservation(String input) throws SQLException {
        String params = parseParams(input);
        String date = params.split(",")[0];
        String time = params.split(",")[1];
        String name = params.split(",")[2];
        String themeId = params.split(",")[3];
        Long id = reservationService.createReservation(
                new ReservationRequestDTO(LocalDate.parse(date), LocalTime.parse(time + ":00"), name, 1L));
        ThemeResponseDto themeResponseDto = themeService.findTheme(Long.parseLong(themeId));
        return new Reservation(id, LocalDate.parse(date), LocalTime.parse(time + ":00"), name,
                new Theme(themeResponseDto.getName(), themeResponseDto.getDescription(), themeResponseDto.getPrice()));
    }


}

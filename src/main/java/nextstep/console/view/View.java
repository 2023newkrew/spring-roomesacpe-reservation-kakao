package nextstep.console.view;

import nextstep.dto.ReservationResponseDTO;
import nextstep.dto.ThemeResponseDto;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;

public class View {

    private final InputView inputView = new InputView();

    private final OutputView outputView = new OutputView();


    // Output
    public void printCommand(){
        outputView.printCommand();
    }

    public void printReservation(Reservation reservation){
        outputView.printReservation(reservation);
    }

    public void printReservationResponseDto(ReservationResponseDTO responseDTO){
        outputView.printReservationResponseDto(responseDTO);
    }

    public void printThemeResponseDto(ThemeResponseDto themeResponseDto) {
        outputView.printThemeResponseDto(themeResponseDto);
    }

    public void printTheme(Theme theme) {
        outputView.printTheme(theme);
    }

    public void printErrorMessage(Exception e, String... messages) {
        outputView.printErrorMessage(e, messages);
    }
    // Input

    public String readLine(){
        return inputView.readLine();
    }
}

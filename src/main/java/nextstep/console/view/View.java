package nextstep.console.view;

import nextstep.dto.ReservationResponseDto;
import nextstep.dto.ThemeResponseDto;

public class View {

    private final InputView inputView;

    private final OutputView outputView;

    public View(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    // Output
    public void printCommand() {
        outputView.printCommand();
    }

    public void printReservationResponseDto(ReservationResponseDto responseDTO) {
        outputView.printReservationResponseDto(responseDTO);
    }

    public void printThemeResponseDto(ThemeResponseDto themeResponseDto) {
        outputView.printThemeResponseDto(themeResponseDto);
    }

    public void printErrorMessage(Exception e, String... messages) {
        outputView.printErrorMessage(e, messages);
    }
    // Input

    public String readLine() {
        return inputView.readLine();
    }
}

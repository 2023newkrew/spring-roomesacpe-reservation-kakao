package nextstep.console.view;

import nextstep.dto.ReservationResponseDTO;
import nextstep.entity.Reservation;

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

    // Input

    public String readLine(){
        return inputView.readLine();
    }



}

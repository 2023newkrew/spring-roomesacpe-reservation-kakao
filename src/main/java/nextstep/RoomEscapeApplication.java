package nextstep;

import nextstep.console.controller.RoomEscapeController;
import nextstep.console.view.RoomEscapeInput;
import nextstep.console.view.RoomEscapeOutput;
import nextstep.reservation.repository.ReservationRepository;
import nextstep.reservation.repository.SimpleReservationRepository;
import nextstep.reservation.service.ReservationService;
import nextstep.reservation.service.ReservationServiceImpl;

public class RoomEscapeApplication {


    public static void main(String[] args) {
        createController().run();
    }

    private static RoomEscapeController createController() {
        RoomEscapeInput input = new RoomEscapeInput(System.in);
        RoomEscapeOutput output = new RoomEscapeOutput();
        ReservationService service = createService();

        return new RoomEscapeController(input, output, service);
    }

    private static ReservationService createService() {
        ReservationRepository repository = new SimpleReservationRepository();

        return new ReservationServiceImpl(repository);
    }
}

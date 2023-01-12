package nextstep;

import nextstep.etc.console.RoomEscapeController;
import nextstep.etc.console.RoomEscapeInput;
import nextstep.etc.console.RoomEscapeOutput;
import nextstep.reservation.dao.ReservationDAO;
import nextstep.reservation.dao.SimpleReservationDAO;
import nextstep.reservation.repository.ReservationRepository;
import nextstep.reservation.repository.ReservationRepositoryImpl;
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
        ReservationDAO dao = new SimpleReservationDAO();
        ReservationRepository repository = new ReservationRepositoryImpl(dao);

        return new ReservationServiceImpl(repository);
    }
}

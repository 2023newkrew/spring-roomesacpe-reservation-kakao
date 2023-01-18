package nextstep.web.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.domain.dto.ReservationRequest;
import nextstep.domain.dto.ReservationResponse;
import nextstep.domain.dto.ThemeResponse;
import nextstep.domain.repository.ReservationRepository;
import nextstep.domain.repository.ThemeRepository;
import nextstep.domain.service.ReservationService;
import org.springframework.stereotype.Service;

@Service
public class ReservationWebService implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationWebService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Reservation newReservation(ReservationRequest reservationRequest){

        if (reservationRepository.duplicate(reservationRequest.getDate(), reservationRequest.getTime())) {
            throw new IllegalArgumentException();
        }

        Reservation reservation = new Reservation(
                reservationRequest.getDate(),
                reservationRequest.getTime(),
                reservationRequest.getName(),
                reservationRequest.getThemeId());
        return this.reservationRepository.create(reservation);
    }

    public ReservationResponse findReservation(long id){
        Reservation result = this.reservationRepository.find(id);
        Theme theme = this.themeRepository.find(result.getThemeId());
        ThemeResponse themeResponse = theme.generateResponse();
        return new ReservationResponse(result.getId(),result.getDate(),result.getTime(),result.getName(),themeResponse);
    }

    public boolean deleteReservation(long id){
        return this.reservationRepository.delete(id);
    }
}

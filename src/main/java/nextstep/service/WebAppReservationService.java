package nextstep.service;

import nextstep.domain.theme.Theme;
import nextstep.domain.dto.CreateReservationDTO;
import nextstep.domain.dto.GetReservationDTO;
import nextstep.domain.reservation.Reservation;
import nextstep.repository.WebAppReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class WebAppReservationService {

    private WebAppReservationRepository webAppReservationRepository;

    @Autowired
    public WebAppReservationService(WebAppReservationRepository webAppReservationRepository) {
        this.webAppReservationRepository = webAppReservationRepository;
    }

    public long addReservation(CreateReservationDTO reservationDto) {
        Reservation reservation = new Reservation(
                LocalDate.parse(reservationDto.getLocalDate()),
                LocalTime.parse(reservationDto.getLocalTime()),
                reservationDto.getName(),
                new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
        );
        int duplicatedCount = webAppReservationRepository.countByDateAndTime(
                Date.valueOf(reservation.getDate()),
                Time.valueOf(reservation.getTime())
        );
        if (duplicatedCount > 0) {
            return -1;
        }
        return webAppReservationRepository.add(reservation);
    }

    public GetReservationDTO getReservation(Long id) {
        return new GetReservationDTO(webAppReservationRepository.findById(id).orElseThrow());
    }

    public void deleteReservation(Long id) {
        webAppReservationRepository.delete(id);
    }

}

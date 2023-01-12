package nextstep.web.service;

import nextstep.domain.Reservation;
import nextstep.web.dto.CreateReservationRequestDto;
import nextstep.web.dto.FindReservationResponseDto;
import nextstep.web.repository.RoomEscapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final RoomEscapeRepository<Reservation> reservationRepository;

    @Autowired
    public ReservationService(@Qualifier("reservationDao") RoomEscapeRepository<Reservation> reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long createReservation(CreateReservationRequestDto requestDto) {
        Reservation reservation = Reservation.of(
                requestDto.getDate(),
                requestDto.getTime(),
                requestDto.getName(),
                requestDto.getThemeId()
        );

        return reservationRepository.save(reservation);
    }

    public FindReservationResponseDto findReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id);

        return FindReservationResponseDto.of(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}

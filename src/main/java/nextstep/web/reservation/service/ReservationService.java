package nextstep.web.reservation.service;

import nextstep.domain.Reservation;
import nextstep.web.reservation.dto.CreateReservationRequestDto;
import nextstep.web.reservation.dto.CreateReservationResponseDto;
import nextstep.web.reservation.dto.FindReservationResponseDto;
import nextstep.web.common.repository.RoomEscapeRepository;
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

    public CreateReservationResponseDto createReservation(CreateReservationRequestDto requestDto) {
        Reservation reservation = Reservation.from(requestDto);

        return CreateReservationResponseDto.from(reservationRepository.save(reservation));
    }

    public FindReservationResponseDto findReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id);

        return FindReservationResponseDto.of(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}

package nextstep.roomescape.reservation;

import nextstep.roomescape.reservation.domain.dto.ReservationResponseDTO;
import nextstep.roomescape.reservation.domain.entity.Reservation;
import nextstep.roomescape.reservation.domain.dto.ReservationRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation create(ReservationRequestDTO reservation) {
        return reservationRepository.create(reservation.toEntity());
    }

    @Override
    public ReservationResponseDTO findById(long id) {
        return ReservationResponseDTO.of(reservationRepository.findById(id));
    }

    @Override
    public Boolean delete(long id) {
        return reservationRepository.delete(id);
    }
}

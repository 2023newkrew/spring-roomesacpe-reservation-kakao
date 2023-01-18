package nextstep.roomescape.service;

import nextstep.roomescape.exception.NotExistEntityException;
import nextstep.roomescape.repository.ReservationRepository;
import nextstep.roomescape.controller.ResponseDTO.ReservationResponseDTO;
import nextstep.roomescape.controller.RequestDTO.ReservationRequestDTO;
import nextstep.roomescape.repository.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Long create(ReservationRequestDTO reservation) {
        System.out.println(reservation.getTheme().getName());
        return reservationRepository.create(reservation.toEntity());
    }

    @Override
    public ReservationResponseDTO findById(long id) {
        return ReservationResponseDTO.of(reservationRepository.findById(id));
    }

    @Override
    public void delete(long id) {
        Reservation reservation = reservationRepository.findById(id);
        if (reservation == null) {
            throw new NotExistEntityException("해당 id가 없습니다.");
        }
        reservationRepository.delete(id);
    }

    @Override
    public List<Reservation> findByThemeId(Long id) {
        return reservationRepository.findByThemeId(id);
    }
}

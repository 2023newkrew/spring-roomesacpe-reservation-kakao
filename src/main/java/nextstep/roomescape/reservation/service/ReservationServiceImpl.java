package nextstep.roomescape.reservation.service;

import nextstep.roomescape.exception.NotExistEntityException;
import nextstep.roomescape.reservation.repository.ReservationRepository;
import nextstep.roomescape.reservation.controller.dto.ReservationResponseDTO;
import nextstep.roomescape.reservation.controller.dto.ReservationRequestDTO;
import nextstep.roomescape.reservation.repository.model.Reservation;
import nextstep.roomescape.theme.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    @Override
    public Long create(ReservationRequestDTO reservation) {
        if (themeRepository.find(reservation.getTheme()) == null) {
            System.out.println("입력된 테마 정보가 잘못되었습니다.");
            throw new NotExistEntityException("입력된 테마 정보가 잘못되었습니다.");
        }
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
}

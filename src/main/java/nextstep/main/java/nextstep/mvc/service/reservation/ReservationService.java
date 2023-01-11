package nextstep.main.java.nextstep.mvc.service.reservation;

import lombok.RequiredArgsConstructor;
import nextstep.main.java.nextstep.mvc.domain.reservation.response.ReservationFindResponse;
import nextstep.main.java.nextstep.mvc.domain.reservation.request.ReservationCreateRequest;
import nextstep.main.java.nextstep.mvc.domain.reservation.ReservationMapper;
import nextstep.main.java.nextstep.global.exception.exception.DuplicateReservationException;
import nextstep.main.java.nextstep.global.exception.exception.NoSuchReservationException;
import nextstep.main.java.nextstep.mvc.repository.reservation.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public Long save(ReservationCreateRequest request) {
        if (reservationRepository.existsByDateAndTime(request.getDate(), request.getTime())) {
            throw new DuplicateReservationException();
        }
        return reservationRepository.save(request);
    }

    public ReservationFindResponse findOneById(Long id) {
        return reservationMapper.reservationToFindResponse(
                reservationRepository.findById(id).orElseThrow(NoSuchReservationException::new)
        );
    }

    public void deleteOneById(Long id) {
        reservationRepository.deleteOne(id);
    }
}

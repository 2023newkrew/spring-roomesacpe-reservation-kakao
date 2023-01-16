package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.etc.exception.ErrorMessage;
import nextstep.etc.exception.ReservationException;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.domain.Theme;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.dto.ReservationResponse;
import nextstep.reservation.mapper.ReservationMapper;
import nextstep.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationServiceImpl implements ReservationService {

    private final Theme THEME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private final ReservationRepository repository;

    private final ReservationMapper mapper;

    @Transactional
    @Override
    public ReservationResponse create(ReservationRequest request) {
        Reservation reservation = mapper.fromRequest(request, THEME);
        if (repository.existsByDateAndTime(reservation)) {
            throw new ReservationException(ErrorMessage.RESERVATION_CONFLICT);
        }
        reservation = repository.insert(reservation);

        return mapper.toResponse(reservation);
    }

    @Override
    public ReservationResponse getById(Long id) {
        Reservation reservation = repository.getById(id);

        return mapper.toResponse(reservation);
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }
}

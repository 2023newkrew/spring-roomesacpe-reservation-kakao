package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.domain.Theme;
import nextstep.reservation.dto.ReservationDTO;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.mapper.ReservationMapper;
import nextstep.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final Theme THEME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private final ReservationRepository repository;

    @Override
    public Long create(ReservationRequest request) {
        Reservation reservation = ReservationMapper.INSTANCE.fromRequest(request, THEME);
        if (repository.existsByDateAndTime(reservation.getDate(), reservation.getTime())) {
            throw new RuntimeException("해당 시각에는 이미 예약이 존재합니다.");
        }

        return repository.insert(reservation);
    }

    @Override
    public ReservationDTO getById(Long id) {
        Reservation reservation = repository.getById(id);

        return ReservationMapper.INSTANCE.toDto(reservation);
    }

    @Override
    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }
}

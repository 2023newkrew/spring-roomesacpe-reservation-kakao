package nextstep.web.repository;

import nextstep.domain.Reservation;

import java.util.Optional;

public interface ReservationRepository {

    Reservation findById(Long id);

    Optional<Reservation> findByThemeId(Long themeId);

    Long save(Reservation reservation);

    void deleteById(Long id);
}

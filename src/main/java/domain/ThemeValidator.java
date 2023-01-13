package domain;

import kakao.dto.request.CreateThemeRequest;
import kakao.error.exception.DuplicatedThemeException;
import kakao.error.exception.UsingThemeException;
import kakao.repository.ReservationJDBCRepository;
import kakao.repository.ThemeJDBCRepository;

public class ThemeValidator {

    private final ThemeJDBCRepository themeJDBCRepository;
    private final ReservationJDBCRepository reservationJDBCRepository;

    public ThemeValidator(ThemeJDBCRepository themeJDBCRepository, ReservationJDBCRepository reservationJDBCRepository) {
        this.themeJDBCRepository = themeJDBCRepository;
        this.reservationJDBCRepository = reservationJDBCRepository;
    }

    public void validateForUpdate(Long themeId) {
        if (!reservationJDBCRepository.findByRequestId(themeId).isEmpty())
            throw new UsingThemeException();
    }

    public void validateForCreate(CreateThemeRequest request) {
        if (!themeJDBCRepository.findByName(request.name).isEmpty())
            throw new DuplicatedThemeException();
    }
}

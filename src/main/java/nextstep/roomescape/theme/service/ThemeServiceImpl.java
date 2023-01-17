package nextstep.roomescape.theme.service;

import nextstep.roomescape.exception.NotExistEntityException;
import nextstep.roomescape.exception.UsedExistEntityException;
import nextstep.roomescape.reservation.repository.ReservationRepository;
import nextstep.roomescape.reservation.repository.ReservationRepositoryJdbcImpl;
import nextstep.roomescape.theme.controller.dto.ThemeRequestDTO;
import nextstep.roomescape.theme.repository.ThemeRepository;
import nextstep.roomescape.theme.repository.model.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private final ReservationRepository reservationRepository;
    @Autowired
    public ThemeServiceImpl(ThemeRepository themeRepository, ReservationRepositoryJdbcImpl reservationRepository) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
    }
    @Override
    public Long create(ThemeRequestDTO themeRequestDTO) {
        return themeRepository.create(themeRequestDTO.toEntity());
    }

    @Override
    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    @Override
    public void delete(long id) {
        Theme theme = themeRepository.findById(id);
        if (theme == null){
            throw new NotExistEntityException("해당 id 테마는 존재하지 않습니다.");
        }
        if (reservationRepository.findByThemeId(id).size() > 0){
            throw new UsedExistEntityException("해당 테마는 예약이 있어 삭제가 불가능합니다.");
        }
        themeRepository.delete(id);
    }
}

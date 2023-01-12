package nextstep.web.theme.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.common.exception.BusinessException;
import nextstep.web.common.exception.CommonErrorCode;
import nextstep.web.reservation.repository.ReservationDao;
import nextstep.web.theme.dto.*;
import nextstep.web.common.repository.RoomEscapeRepository;
import nextstep.web.theme.repository.ThemeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private final RoomEscapeRepository<Theme> themeRepository;
    private final RoomEscapeRepository<Reservation> reservationRepository;

    @Autowired
    public ThemeService(@Qualifier("themeDao") RoomEscapeRepository<Theme> themeRepository,
                        @Qualifier("reservationDao") RoomEscapeRepository<Reservation> reservationRepository) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
    }

    public CreateThemeResponseDto createTheme(CreateThemeRequestDto requestDto) {
        Theme theme = Theme.from(requestDto);

        return CreateThemeResponseDto.from(themeRepository.save(theme));
    }

    public FindThemeResponseDto findTheme(Long id) {
        Theme theme = themeRepository.findById(id);

        return FindThemeResponseDto.of(theme);
    }

    public FindAllThemeResponseDto findAllTheme() {
        return new FindAllThemeResponseDto(themeRepository.findAll());
    }

    public void deleteTheme(Long id) {
        if (isReserved(id)) {
            throw new BusinessException(CommonErrorCode.RESERVED_THEME_ERROR);
        }
        themeRepository.deleteById(id);
    }

    public void updateTheme(CreateThemeRequestDto requestDto, Long id) {
        if (isReserved(id)) {
            throw new BusinessException(CommonErrorCode.RESERVED_THEME_ERROR);
        }
        if (((ThemeDao) themeRepository).updateById(Theme.of(requestDto, id)) == 0) {
            throw new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    private boolean isReserved(Long themeId) {
        return ((ReservationDao) reservationRepository).findByThemeId(themeId).size() == 0;
    }
}

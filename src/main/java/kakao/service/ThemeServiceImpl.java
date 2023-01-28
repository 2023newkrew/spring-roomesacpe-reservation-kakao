package kakao.service;

import kakao.controller.request.ThemeRequest;
import kakao.controller.response.ThemeResponse;
import kakao.exception.CorrespondingReservationExistException;
import kakao.exception.DuplicatedThemeException;
import kakao.repository.ReservationRepository;
import kakao.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeServiceImpl implements ThemeService {
    ReservationRepository reservationRepository;
    ThemeRepository themeRepository;

    public ThemeServiceImpl(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    @Override
    public Long create(ThemeRequest themeRequest) {
        checkIfDuplicatedTheme(themeRequest);

        return themeRepository.create(themeRequest);
    }

    @Override
    public List<ThemeResponse> findAll() {
        return themeRepository.findAll().stream()
                .map(ThemeResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        checkIfRelatedReservationExist(id);
        themeRepository.deleteById(id);
    }

    private void checkIfRelatedReservationExist(Long id) {
        if(reservationRepository.findByThemeId(id).size() > 0) {
            throw new CorrespondingReservationExistException();
        }
    }
    private void checkIfDuplicatedTheme(ThemeRequest themeRequest) {
        if(themeRepository.findByName(themeRequest.getName()).isPresent()) {
            throw new DuplicatedThemeException();
        }
    }
}

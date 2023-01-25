package web.service;

import org.springframework.stereotype.Service;
import web.domain.Reservation;
import web.domain.Theme;
import web.dto.request.ReservationRequestDTO;
import web.dto.response.ReservationIdDto;
import web.dto.response.ReservationResponseDTO;
import web.exception.DuplicatedReservationException;
import web.repository.ReservationJdbcRepository;
import web.repository.ThemeJdbcRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationJdbcRepository reservationJdbcRepository;
    private final ThemeJdbcRepository themeJdbcRepository;

    public ReservationService(ReservationJdbcRepository reservationJdbcRepository, ThemeJdbcRepository themeJdbcRepository) {
        this.reservationJdbcRepository = reservationJdbcRepository;
        this.themeJdbcRepository = themeJdbcRepository;
    }

    public ReservationIdDto createReservation(ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = reservationRequestDTO.toEntity();

        validateThemeExist(reservation.getThemeId());
        validateReservation(reservation);

        return reservationJdbcRepository.createReservation(reservation);
    }

    private void validateThemeExist(Long themeId) {
        themeJdbcRepository.findThemeById(themeId);
    }

    private void validateReservation(Reservation reservation) {

        List<Long> ids = reservationJdbcRepository.findAllReservationWithDateAndTime(reservation);

        if (ids.size() > 0) {
            throw new DuplicatedReservationException();
        }
    }

    public ReservationResponseDTO getReservationById(Long id) {
        Reservation reservation = reservationJdbcRepository.findReservationById(id);
        Theme theme = themeJdbcRepository.findThemeById(reservation.getThemeId());

        return ReservationResponseDTO.from(reservation, theme);
    }

    public void deleteReservationById(Long id) {
        reservationJdbcRepository.findReservationById(id);
        reservationJdbcRepository.deleteReservationById(id);
    }
}

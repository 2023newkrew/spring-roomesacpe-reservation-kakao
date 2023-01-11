package nextstep.service;

import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;
import nextstep.entity.Reservation;
import nextstep.exception.ConflictException;
import nextstep.exception.DatabaseServerException;
import nextstep.exception.ExceptionMetadata;
import nextstep.exception.NotFoundException;
import nextstep.mapstruct.ReservationMapper;
import nextstep.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Long createReservation(ReservationRequestDTO reservationRequestDTO) throws SQLException {

        validate(reservationRequestDTO);

        return reservationRepository.save(reservationRequestDTO);
    }

    private void validate(ReservationRequestDTO reservationRequestDTO) {
        boolean isExist;
        try {
            isExist = reservationRepository.existByDateAndTime(
                    reservationRequestDTO.getDate(),
                    reservationRequestDTO.getTime());
        } catch (SQLException e) {
            throw new DatabaseServerException(ExceptionMetadata.UNEXPECTED_DATABASE_SERVER_ERROR);
        }

        if (isExist) {
            throw new ConflictException(ExceptionMetadata.DUPLICATE_RESERVATION_BY_DATE_AND_TIME);
        }
    }

    @Override
    public ReservationResponseDTO findReservation(Long id) throws SQLException {
        try {
            Reservation reservation = reservationRepository.findById(id);
            return ReservationMapper.INSTANCE.reservationToResponseDTO(reservation);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        if (reservationRepository.deleteById(id) == 0) {
            throw new NotFoundException(ExceptionMetadata.UNEXPECTED_DATABASE_SERVER_ERROR);
        }
    }
}

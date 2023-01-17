package nextstep.service;

import java.time.LocalTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import nextstep.dto.ReservationRequestDTO;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import nextstep.entity.TimeTable;
import nextstep.exception.ConflictException;
import nextstep.exception.NotFoundException;
import nextstep.repository.ReservationRepository;

@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public Reservation createReservation(ReservationRequestDTO reservationRequestDTO, Theme theme) {
        return reservationRepository.save(reservationRequestDTO.toEntity(theme));
    }

    @Override
    public Reservation findReservationByID(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (Objects.nonNull(reservation)) {
            return reservation;
        }
        return null;
    }

    @Override
    public void deleteById(Long id){
        if (reservationRepository.deleteById(id) == 0) {
            throw new NotFoundException("해당 예약이 존재하지 않습니다.");
        }
    }

    @Override
    public boolean existByThemeId(Long id) {
        return reservationRepository.existByThemeId(id);
    }


    @Override
    public void validateCreateReservation(ReservationRequestDTO reservationRequestDTOvation) {
        validateTime(reservationRequestDTOvation.getTime());
        validateDuplicate(reservationRequestDTOvation);
    }

    private void validateTime(LocalTime time) {
        if(!TimeTable.contains(time)){
            throw new ConflictException("해당하는 시간에 테마가 존재하지 않습니다.");
        }
    }

    private void validateDuplicate(ReservationRequestDTO reservationRequestDTO) {
        boolean isExist = reservationRepository.existByDateAndTimeAndThemeId(reservationRequestDTO.getDate(),
                reservationRequestDTO.getTime(), reservationRequestDTO.getThemeId());
        if (isExist) {
            throw new ConflictException("날짜와 시간이 중복되는 예약은 생성할 수 없습니다.");
        }
    }
}

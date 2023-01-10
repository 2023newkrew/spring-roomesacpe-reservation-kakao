package nextstep.dao;

import nextstep.domain.Themes;
import nextstep.entity.Reservation;
import nextstep.domain.Reservations;
import nextstep.dto.ReservationDto;

import java.util.List;


public class ReservationDao {

    private final Reservations reservations = Reservations.getInstance();
    private final Themes themes = Themes.getInstance();

    public Long createReservation(ReservationDto reservationDto){
        if(reservations.findByDateTime(reservationDto.getLocalDate(), reservationDto.getLocalTime()) != null){
            throw new IllegalArgumentException("같은 날짜와 시간에 예약할 수 없습니다.");
        }

        if (themes.findById(reservationDto.getThemeId()) == null){
            throw new IllegalArgumentException("없는 테마입니다.");
        }
        return reservations.add(reservationDto);
    }

    public void deleteReservation(Long id) {
        reservations.deleteById(id);
    }

    public Reservation findById(Long id){
        return reservations.findById(id);
    }

    public List<Reservation> findAll(){
        return reservations.findAll();
    }
}

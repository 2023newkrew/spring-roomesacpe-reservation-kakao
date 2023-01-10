package roomescape.repository;


import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ReservationConsoleRepository implements ReservationRepository{

    @Override
    public void insertReservation(Reservation reservation) {

    }

    @Override
    public Optional<Reservation> getReservation(Long id) {
        return Optional.empty();
    }

    @Override
    public int deleteReservation(Long id) {
        return 0;
    }

    @Override
    public Optional<Reservation> getReservationByDateAndTime(LocalDate date, LocalTime time) {
        return Optional.empty();
    }
}

package roomescape.service.Reservation;


import roomescape.domain.Reservation;


public interface ReservationService {
    Reservation createReservation(Reservation reservation);
    Reservation lookUpReservation(Long reserveId);
    void deleteReservation(Long deleteId);
}

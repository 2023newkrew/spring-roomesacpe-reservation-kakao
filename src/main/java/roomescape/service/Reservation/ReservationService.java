package roomescape.service.Reservation;


import roomescape.domain.Reservation;


public interface ReservationService {
    String createReservation(Reservation reservation);
    String lookUpReservation(Long reserveId);
    void deleteReservation(Long deleteId);
}

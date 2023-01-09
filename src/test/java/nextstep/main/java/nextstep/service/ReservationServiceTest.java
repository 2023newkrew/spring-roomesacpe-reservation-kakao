package nextstep.main.java.nextstep.service;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.repository.MemoryReservationRepository;
import nextstep.main.java.nextstep.repository.ReservationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationServiceTest {


    private static final ReservationService service = new ReservationService(new MemoryReservationRepository());
    @BeforeEach
    void setUp(){
        MemoryReservationRepository.reservationMap
                .put(1L,new Reservation(1L, LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name", null));
        MemoryReservationRepository.reservationMap
                .put(2L,new Reservation(2L, LocalDate.of(2025, 1, 9), LocalTime.of(1, 30), "reservation2", null));
    }

    @AfterEach
    void tearDown() {
        MemoryReservationRepository.reservationMap.clear();
    }

    @Test
    void findByOneIdTest(){
        Reservation reservation = MemoryReservationRepository.reservationMap.get(1L);
        Assertions.assertThat(service.findOneById(1L)).isEqualTo(reservation);
    }
}

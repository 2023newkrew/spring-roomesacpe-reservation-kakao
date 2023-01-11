package nextstep.main.java.nextstep.service;

@Deprecated
public class ReservationServiceTest {

//    private static final ReservationService service = new ReservationService(new MemoryReservationRepository());
//    @BeforeEach
//    void setUp(){
//        MemoryReservationRepository.reservationMap
//                .put(1L,new Reservation(1L, LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name", null));
//        MemoryReservationRepository.reservationMap
//                .put(2L,new Reservation(2L, LocalDate.of(2025, 1, 9), LocalTime.of(1, 30), "reservation2", null));
//    }
//
//    @AfterEach
//    void tearDown() {
//        MemoryReservationRepository.reservationMap.clear();
//    }
//
//    @Test
//    @DisplayName("예약 단건 조회 테스트")
//    void findOneByIdTest() {
//        Reservation reservation = MemoryReservationRepository.reservationMap.get(1L);
//        assertThat(service.findOneById(1L)).isEqualTo(reservation);
//    }
//
//    @Test
//    @DisplayName("예약 단건 삭제 테스트")
//    void deleteOneByIdTest() {
//        assertThatCode(() -> service.findOneById(1L)).doesNotThrowAnyException();
//
//        service.deleteOneById(1L);
//        assertThatThrownBy(() -> service.findOneById(1L)).isInstanceOf(NoSuchReservationException.class);
//    }
//
//    @Test
//    @DisplayName("예약 중복 등록 테스트")
//    void createDuplicateTest(){
//        ReservationCreateRequest requestDto = new ReservationCreateRequest(LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name");
//            assertThatThrownBy(() -> service.save(requestDto)).isInstanceOf(DuplicateReservationException.class);
//    }

}

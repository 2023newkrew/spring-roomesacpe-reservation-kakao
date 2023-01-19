package nextstep.console.view;

import nextstep.console.service.ReservationConsoleService;
import nextstep.domain.Reservation;
import nextstep.domain.dto.ReservationRequest;
import nextstep.domain.dto.ReservationResponse;
import nextstep.domain.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationView {
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";

    private final ReservationService service;

    public ReservationView() {
        service = new ReservationConsoleService();
    }

    public void parseInput(String input){
        if (input.startsWith(ADD)) {
            String params = input.split(" ")[1];

            String date = params.split(",")[0];
            String time = params.split(",")[1];
            String name = params.split(",")[2];
            String themeId = params.split(",")[3];

            ReservationRequest reservationRequest = new ReservationRequest(
                    LocalDate.parse(date),
                    LocalTime.parse(time + ":00"),
                    name,
                    Long.parseLong(themeId)
            );

            Reservation reservation = service.newReservation(reservationRequest);

            System.out.println("예약이 등록되었습니다.");
            System.out.println("예약 번호: " + reservation.getId());
            System.out.println("예약 날짜: " + reservation.getDate());
            System.out.println("예약 시간: " + reservation.getTime());
            System.out.println("예약자 이름: " + reservation.getName());
        }

        if (input.startsWith(FIND)) {
            String params = input.split(" ")[1];

            Long id = Long.parseLong(params.split(",")[0]);

            ReservationResponse reservation = service.findReservation(id);

            System.out.println("예약 번호: " + reservation.getId());
            System.out.println("예약 날짜: " + reservation.getDate());
            System.out.println("예약 시간: " + reservation.getTime());
            System.out.println("예약자 이름: " + reservation.getName());
            System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
            System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
            System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
        }

        if (input.startsWith(DELETE)) {
            String params = input.split(" ")[1];

            Long id = Long.parseLong(params.split(",")[0]);

            if (service.deleteReservation(id)) {
                System.out.println("예약이 취소되었습니다.");
            }
        }

    }
}

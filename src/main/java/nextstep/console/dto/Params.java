package nextstep.console.dto;

import nextstep.reservation.dto.ReservationRequest;

import java.util.List;
import java.util.Objects;

public class Params {

    private static final long THEME_ID = 1L;

    private final List<String> params;

    public Params() {
        this(List.of());
    }

    public Params(List<String> params) {
        if (Objects.isNull(params)) {
            params = List.of();
        }

        this.params = params;
    }

    public int size() {
        return params.size();
    }

    public ReservationRequest getReservationRequest() {
        String date = params.get(0);
        String time = params.get(1);
        String name = params.get(2);

        return new ReservationRequest(date, time, name, THEME_ID);
    }

    public Long getLong() {
        return Long.parseLong(params.get(0));
    }
}

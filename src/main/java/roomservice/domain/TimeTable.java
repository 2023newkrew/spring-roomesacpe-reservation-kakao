package roomservice.domain;

import java.time.LocalTime;

public enum TimeTable {
    A(LocalTime.of(11, 30)),
    B(LocalTime.of(13, 00)),
    C(LocalTime.of(14, 30)),
    D(LocalTime.of(16, 00)),
    E(LocalTime.of(17, 30)),
    F(LocalTime.of(19, 00)),
    G(LocalTime.of(20, 30)),
    H(LocalTime.of(22, 00));

    private LocalTime time;

    TimeTable(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}

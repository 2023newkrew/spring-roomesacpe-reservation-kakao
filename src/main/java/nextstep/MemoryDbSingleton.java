package nextstep;

import java.util.HashMap;
import java.util.Map;

public class MemoryDbSingleton {
    private static final MemoryDbSingleton memoryDbSingleton = new MemoryDbSingleton();
    private static final Map<Long, Reservation> reservationMap = new HashMap<>();

    private static Long primaryKey = 1L;

    public static MemoryDbSingleton getMemoryDbSingleton(){
        return memoryDbSingleton;
    }

    public Map<Long, Reservation> getReservationMap(){
        return reservationMap;
    }

    public Long increasePrimaryKey() {
        return primaryKey++;
    }

    public Long getPrimaryKey() {
        return primaryKey;
    }
}

package nextstep.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AutoIncrementGenerator {

    private static final Map<Class<?>, AtomicLong> autoIncrementMap = new ConcurrentHashMap<>();

    public static Long getId(Class<?> classType) {
        if (!autoIncrementMap.containsKey(classType)) {
            autoIncrementMap.put(classType, new AtomicLong(1L));
        }

        return autoIncrementMap.get(classType).getAndAdd(1L);
    }

}

package nextstep.utils;

import java.util.Objects;

public class NumberUtils {

    public static boolean isNullOrLessThanZero(Number number) {
        return Objects.isNull(number) || number.intValue() < 0;
    }

}

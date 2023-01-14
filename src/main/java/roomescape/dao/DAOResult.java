package roomescape.dao;

import java.util.List;
import roomescape.exception.BadRequestException;

public class DAOResult {

    private static <T> void validateResult(List<T> result) {
        if (result.size() != 1) {
            throw new BadRequestException();
        }
    }

    public static <T> T getResult(List<T> result) {
        validateResult(result);
        return result.get(0);
    }
}

package roomescape.dao;

import java.util.List;

public class DAOResult {

    private static <T> void validateResult(List<T> result) {
        if(result == null || result.size() != 1) {
            throw new RuntimeException();
        }
    }

    public static <T> T getResult(List<T> result) {
        validateResult(result);
        return result.get(0);
    }
}

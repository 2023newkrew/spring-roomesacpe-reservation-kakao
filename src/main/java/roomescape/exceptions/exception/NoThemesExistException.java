package roomescape.exceptions.exception;

public class NoThemesExistException extends RuntimeException {
    private static String NO_THEMES_EXIST = "테마가 한 개도 존재하지 않습니다.";

    public NoThemesExistException() {
        this(NO_THEMES_EXIST);
    }

    public NoThemesExistException(String message) {
        super(message);
    }
}

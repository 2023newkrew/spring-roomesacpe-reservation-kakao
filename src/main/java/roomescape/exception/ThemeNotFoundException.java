package roomescape.exception;

import java.util.NoSuchElementException;

public class ThemeNotFoundException extends NoSuchElementException {

    public ThemeNotFoundException() {
    }

    public ThemeNotFoundException(String s) {
        super(s);
    }

}

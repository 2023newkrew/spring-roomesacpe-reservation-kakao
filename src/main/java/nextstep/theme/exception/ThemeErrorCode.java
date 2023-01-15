package nextstep.theme.exception;

import nextstep.exception.ErrorCode;

public enum ThemeErrorCode implements ErrorCode {
    THEME_NOT_FOUND("존재하지 않는 예약압니다.");

    private final String message;
    ThemeErrorCode(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return null;
    }
}

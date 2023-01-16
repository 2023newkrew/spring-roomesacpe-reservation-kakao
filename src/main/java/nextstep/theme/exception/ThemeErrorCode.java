package nextstep.theme.exception;

import nextstep.exception.ErrorCode;

public enum ThemeErrorCode implements ErrorCode {
    THEME_NOT_FOUND("존재하지 않는 예약압니다."),
    THEME_WITH_RESERVATIONS_CANNOT_MODIFY_OR_DELETE("예약이 존제하는 테마는 수정 또는 삭제할 수 없습니다."),
    THEME_NAME_CANNOT_BE_DUPLICATED("테마 이름은 중복될 수 없습니다.");

    private final String message;
    ThemeErrorCode(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return null;
    }
}

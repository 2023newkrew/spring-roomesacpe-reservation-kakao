package roomescape.utils;

public enum Messages {
    CONSOLE_DESCRIPTION("""
            ### 명령어를 입력하세요. ###
            - 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현,22
            - 예약조회: find {id} ex) find 1
            - 예약취소: delete {id} ex) delete 1
            - 종료: quit
            """),
    RESERVATION_REGISTERED("예약이 등록되었습니다."),
    RESERVATION_ID("예약 번호: "),
    RESERVATION_DATE("예약 날짜: "),
    RESERVATION_TIME("예약 시간: "),
    RESERVATION_NAME("예약자 이름: "),
    RESERVATION_THEME_ID("예약 테마 ID: "),
    RESERVATION_CANCEL("예약이 취소되었습니다.");
    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}

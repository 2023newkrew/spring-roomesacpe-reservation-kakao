package roomescape.utils;

public enum Messages {
    CONSOLE_DESCRIPTION("### 명령어를 입력하세요. ### +" + "\n" +
            "- 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현,22" + '\n' +
            " - 예약조회: find {id} ex) find 1" + '\n' +
            " - 예약취소: delete {id} ex) delete 1" + '\n' +
            " - 종료: quit "),
    RESERVATION_REGISTERED("예약이 등록되었습니다."),
    RESERVATION_CANCEL("예약이 취소되었습니다."),
    RESERVATION_ID("ID: "),
    RESERVATION_DATE("Date "),
    RESERVATION_TIME("Time "),
    RESERVATION_NAME("Name: "),
    THEME_ID("ID: "),
    THEME_NAME("Name: "),
    THEME_DESC("Desc: "),
    THEME_PRICE("Price: "),
    CREATE_REQUEST("Create Request, "),
    CREATE_RESPONSE("Create Response, "),
    LOOKUP_REQUEST("LookUp Request, Id: "),
    LOOKUP_RESPONSE("LookUp RESPONSE, Id: "),
    DELETE_REQUEST("Delete Request, Id: "),
    DELETE_RESPONSE("Delete Response, Id: "),
    CREATE_DUPLICATED("Create DuplicatedError, "),
    CREATE_SUCCESS("Create Success, ID: "),
    NOT_FOUND_ERROR("NotFound Error Id: "),
    CREATE_NOT_FOUND_THEME("NotFound Theme Id: "),
    DELETE_THEME_ERROR("Reservation Exists in Theme"),
    DELETE_NOT_FOUND_ERROR("Delete NotFound Error Id: "),
    DELETE_SUCCESS("Delete Success"),
    RESERVATION_CREATE_ERROR("요청한 날짜/시간에 이미 예약이 존재"),
    THEME_CREATE_ERROR("요청한 이름/가격의 테마가 이미 등록되어 있음"),
    THEME_EXISTS_RESERVATION("요청한 테마에 예약정보가 들어있음. Delete Theme ID: "),
    ID_NOT_FOUND_ERROR("요청한 ID의 정보가 존재하지 않음. ID: "),
    NAME_NOT_EMPTY_STRING("이름을 빈 문자열로 저장할 수 없습니다."),
    THEME_NOT_EXISTS("요청한 예약의 테마가 존재하지 않습니다"),
    ID_BIGGER_THAN_ZERO("Id가 0보다 큰 수여야 합니다");


    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}

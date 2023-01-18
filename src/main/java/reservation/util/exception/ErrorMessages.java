package reservation.util.exception;

public class ErrorMessages {

    public static final String RESERVATION_DUPLICATED = "해당 날짜와 시간에 이미 예약이 존재합니다.";
    public static final String RESERVATION_NOT_FOUND = "해당 예약이 존재하지 않습니다.";
    public static final String CONNECTION_ERROR = "DB Connection 연결에 실패하였습니다.";
    public static final String QUERY_ERROR = "쿼리 실행에 실패했습니다.";
    public static final String THEME_DUPLICATED = "똑같은 이름을 가진 테마가 존재합니다.";
    public static final String THEME_NOT_FOUND = "테마가 존재하지 않습니다.";
    public static final String THEME_RESERVATION_EXIST = "해당 테마를 가지는 예약이 존재합니다.";
}

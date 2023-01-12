package nextstep.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionMetadata {

    UNEXPECTED_DATABASE_SERVER_ERROR("데이터베이스 서버 연결에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    DUPLICATE_RESERVATION_BY_DATE_AND_TIME("날짜와 시간이 중복되는 예약은 생성할 수 없습니다.", HttpStatus.CONFLICT),


    ;

    private final String message;

    private final HttpStatus httpStatus;
}

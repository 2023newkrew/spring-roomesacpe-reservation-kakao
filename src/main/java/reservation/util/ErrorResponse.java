package reservation.util;

public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(ErrorStatus errorStatus) {
        this.status = errorStatus.getStatus();
        this.message = errorStatus.getMessage();
    }

    // Getter 코드가 없으면 406에러 (Could not find acceptable representation) 뱉는다.
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

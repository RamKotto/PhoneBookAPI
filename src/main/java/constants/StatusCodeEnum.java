package constants;

public enum StatusCodeEnum {
    SC_OK(200),
    BAD_REQUEST(400),
    ACCEPTED(202);

    private final int code;

    StatusCodeEnum(int code) {
        this.code = code;
    }

    public int getStatusCode() {
        return code;
    }
}

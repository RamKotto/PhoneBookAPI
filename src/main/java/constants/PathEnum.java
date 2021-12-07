package constants;

public enum PathEnum {
    USERS("/users");

    private final String method;

    PathEnum(String method) {
        this.method = method;
    }

    public String getApiMethod() {
        return method;
    }
}

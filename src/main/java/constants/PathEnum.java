package constants;

public enum PathEnum {
    USERS("/users"),
    USER_CONTACTS("/users/%d/contacts"),
    USER_CONTACT_BY_ID("/users/%d/contacts/%d");

    private final String method;

    PathEnum(String method) {
        this.method = method;
    }

    public String getApiMethod() {
        return method;
    }
}

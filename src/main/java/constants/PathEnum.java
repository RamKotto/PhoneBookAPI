package constants;

public enum PathEnum {
    GET_USERS("/users");
//    WALL_POST("wall.post?"),
//    WALL_EDIT("wall.edit?"),
//    ADD_COMMENT("wall.createComment?"),
//    GET_LIKES("likes.getList?"),
//    DELETE_POST("wall.delete?"),
//    GET_WALL_SERVER("photos.getWallUploadServer?"),
//    SAVE_WALL_PHOTO("photos.saveWallPhoto?"),
//    GET("GET");

    private final String method;

    PathEnum(String method) {
        this.method = method;
    }

    public String getApiMethod() {
        return method;
    }
}

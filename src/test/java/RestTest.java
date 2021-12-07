import Models.UserDTO;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class RestTest {
    private static final String BASE_URI = "http://localhost:8080";
    private static final String USERS_PATH = "/users";

    @Test
    public void getUser() {
        log.debug("Test started...");
        given()
                .baseUri(BASE_URI)
                .basePath(USERS_PATH)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200);
        log.debug("Test finished...");
    }

    @Test
    public void getUsersAsString() {
        log.debug("Test started...");
        String result = given()
                .baseUri(BASE_URI)
                .basePath(USERS_PATH)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .extract().asString();
        log.debug("Result of getUsersAsString: " + result);
        log.debug("Test finished...");
    }

    @Test
    public void getUserAsString() {
        log.debug("Test started...");
        given()
                .baseUri(BASE_URI)
                .basePath(USERS_PATH)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .body("lastName[0]", equalTo("Snow"));
        log.debug("Test finished...");
    }

    @Test
    public void getListOfNames() {
        List<UserDTO> names = given()
                .when()
                .baseUri(BASE_URI)
                .basePath(USERS_PATH)
                .contentType(ContentType.JSON)
                .when().get()
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("", UserDTO.class);
        for (UserDTO us : names) {
            log.debug(us.getId() + " " + us.getFirstName() + " " + us.getLastName());
        }
    }

}

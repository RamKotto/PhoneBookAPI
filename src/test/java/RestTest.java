import dto.UserDTO;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.assertj.core.api.Assertions.assertThat;

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
                // import static org.hamcrest.Matchers.equalTo;
                .body("lastName[0]", equalTo("Snow"));
        log.debug("Test finished...");
    }

    @Test
    public void getListOfUsers() {
        List<UserDTO> users = given()
                .when()
                .baseUri(BASE_URI)
                .basePath(USERS_PATH)
                .contentType(ContentType.JSON)
                .when().get()
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("", UserDTO.class);

        for (UserDTO us : users) {
            log.debug(us.getId() + " " + us.getFirstName() + " " + us.getLastName());
        }

        // Assert J (import static org.assertj.core.api.Assertions.assertThat;)
        assertThat(users).extracting(UserDTO::getFirstName).contains("MarkTwen");
        assertThat(users).extracting(UserDTO::getFirstName).contains("Mwen");
    }

    @Test
    public void createAndDeleteUser() {
        UserDTO newUser = new UserDTO();
        newUser.setFirstName("Dima");
        newUser.setLastName("Saraev");

        UserDTO rs = given()
                .baseUri(BASE_URI)
                .basePath(USERS_PATH)
                .contentType(ContentType.JSON)
                .body(newUser)
                .when().post()
                .then().extract().as(UserDTO.class);

        // Assert J (import static org.assertj.core.api.Assertions.assertThat;)
        assertThat(rs)
                .isNotNull()
                .extracting(UserDTO::getFirstName)
                .isEqualTo(rs.getFirstName());

        List<UserDTO> users = given()
                .when()
                .baseUri(BASE_URI)
                .basePath(USERS_PATH)
                .contentType(ContentType.JSON)
                .when().get()
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("", UserDTO.class);

        Assert.assertTrue(users.contains(rs));

        for (UserDTO us : users) {
            log.debug(us.getId() + " " + us.getFirstName() + " " + us.getLastName());
        }

        assertThat(users).extracting(UserDTO::getFirstName).contains("Dima");

        given()
                .when()
                .baseUri(BASE_URI)
                .basePath(USERS_PATH + "/" + rs.getId())
                .contentType(ContentType.JSON)
                .when().delete()
                .then()
                .statusCode(202);

        users = given()
                .when()
                .baseUri(BASE_URI)
                .basePath(USERS_PATH)
                .contentType(ContentType.JSON)
                .when().get()
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("", UserDTO.class);

        for (UserDTO us : users) {
            log.debug(us.getId() + " " + us.getFirstName() + " " + us.getLastName());
        }

        Assert.assertFalse(users.contains(rs));
    }
}

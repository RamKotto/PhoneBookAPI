package api;

import constants.PathEnum;
import constants.StatusCodeEnum;
import dto.ContactDTO;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import utils.PropertyManager;

import java.util.List;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static constants.PropertiesEnum.CONFIG;

@Slf4j
public class ContactAPI {

    public static <T> T createNewUserContact(ContactDTO contact, int userId, Class<T> clazz) {
        log.info("Creating new contact with params: " + contact.getFirstName() + " " + contact.getPhone());
        return given()
                .baseUri(PropertyManager.propHandler(CONFIG, "HOST"))
                .basePath(format(PathEnum.USER_CONTACTS.getApiMethod(), userId))
                .contentType(ContentType.JSON)
                .body(contact)
                .when().post()
                .then().extract().as(clazz);
    }

    public static String negativeCreateNewUserContact(ContactDTO contact, int userId) {
        log.info("Creating new contact with params: " + contact.getFirstName() + " " + contact.getPhone());
        return given()
                .baseUri(PropertyManager.propHandler(CONFIG, "HOST"))
                .basePath(format(PathEnum.USER_CONTACTS.getApiMethod(), userId))
                .contentType(ContentType.JSON)
                .body(contact)
                .when().post()
                .then().statusCode(StatusCodeEnum.BAD_REQUEST.getStatusCode())
                .extract().asString();
    }

    public static List<ContactDTO> getUserContactList(int userId) {
        log.info("Get contacts for user with id: " + userId);
        return given()
                .baseUri(PropertyManager.propHandler(CONFIG, "HOST"))
                .basePath(format(PathEnum.USER_CONTACTS.getApiMethod(), userId))
                .contentType(ContentType.JSON)
                .when().get()
                .then()
                .statusCode(StatusCodeEnum.SC_OK.getStatusCode())
                .extract().jsonPath().getList("", ContactDTO.class);
    }

    public static void deleteUserContact(int userId, int contactId) {
        log.info("Delete contact with id: " + contactId + " for user with id: " + userId);
        given()
                .baseUri(PropertyManager.propHandler(CONFIG, "HOST"))
                .basePath(format(PathEnum.USER_CONTACT_BY_ID.getApiMethod(), userId, contactId))
                .contentType(ContentType.JSON)
                .when().delete()
                .then().statusCode(StatusCodeEnum.ACCEPTED.getStatusCode());
    }
}

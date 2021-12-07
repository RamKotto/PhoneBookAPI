import dto.ContactDTO;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.RandomUtils;

import java.util.List;

import static api.ContactAPI.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class UserContactTests {
    private static final int DEFAULT_USER_ID = 1;
    private static final int USER_ID_FOR_CHECK_CONTACTS = 2;
    private static final ContactDTO FIRST_CONTACT = new ContactDTO(
            RandomUtils.getRandomWord(8),
            Long.toString(RandomUtils.getRandomPhoneNumber()));
    private static final ContactDTO SECOND_CONTACT = new ContactDTO(
            RandomUtils.getRandomWord(8),
            Long.toString(RandomUtils.getRandomPhoneNumber()));
    private static final ContactDTO FIRST_INCORRECT_CONTACT = new ContactDTO(
            RandomUtils.getRandomWord(8),
            Long.toString(RandomUtils.getRandomPhoneNumber()).substring(8));
    private static final ContactDTO SECOND_INCORRECT_CONTACT = new ContactDTO(
            RandomUtils.getRandomWord(8),
            Long.toString(RandomUtils.getRandomPhoneNumber()).substring(5));

    @DataProvider
    public Object[][] contactData() {
        return new Object[][]{
                {FIRST_CONTACT},
                {SECOND_CONTACT},
        };
    }

    // Тест падает. т.к. есть баг в API (метод DELETE /users/{userId}/contacts/{contactId})
    @Test(dataProvider = "contactData")
    public void userContactTests(ContactDTO contact) {
        // Создать новый контакт для пользователя с ID = 1, и убедиться, что контакт создан.
        ContactDTO newContact = createNewUserContact(contact, DEFAULT_USER_ID, ContactDTO.class);
        assertThat(newContact)
                .isNotNull()
                .extracting(ContactDTO::getPhone)
                .isEqualTo(newContact.getPhone());
        log.info("Contact with name: " + newContact.getFirstName() + " was created.");

        // Проверить, не появился ли созданный контакт у пользователя с ID = 2
        List<ContactDTO> secondUserContacts = getUserContactList(USER_ID_FOR_CHECK_CONTACTS);
        assertThat(secondUserContacts).extracting(ContactDTO::getFirstName).doesNotContain(newContact.getFirstName());
        log.info("Contact with name: " + newContact.getFirstName() + " was not created for user with id = 2.");

        // Удалить созданный контакт, убедиться что контакт успешно удален.
        // Спойлер: он не удалится
        deleteUserContact(DEFAULT_USER_ID, newContact.getId());
        List<ContactDTO> defaultUserContacts = getUserContactList(DEFAULT_USER_ID);
        assertThat(defaultUserContacts).extracting(ContactDTO::getFirstName).doesNotContain(newContact.getFirstName());
    }

    @DataProvider
    public Object[][] incorrectContactData() {
        return new Object[][]{
                {FIRST_INCORRECT_CONTACT},
                {SECOND_INCORRECT_CONTACT},
        };
    }

    @Test(dataProvider = "incorrectContactData")
    public void incorrectPhoneNumberTest(ContactDTO contact) {
        // Аналогично. Как и User в User Test. Возвращает 400 код, но!
        // Ответ возвращается в виде строки JSON:
        // "phone": "должно соответствовать шаблону \"\\d{10}\""
        // Новый котакт не создается. Тест проходит.

        String error = negativeCreateNewUserContact(contact, DEFAULT_USER_ID);
        List<ContactDTO> defaultUserContacts = getUserContactList(DEFAULT_USER_ID);
        assertThat(defaultUserContacts).extracting(ContactDTO::getFirstName).doesNotContain(contact.getFirstName());
        log.info("Contact was not created: " + error);
    }
}

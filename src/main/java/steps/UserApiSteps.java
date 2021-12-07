package steps;

import dto.UserDTO;
import utils.RandomUtils;

import static api.UserApi.updateUser;

public class UserApiSteps {

    public static UserDTO updateCreatedUser(int id) {
        UserDTO updateForUser = new UserDTO(
                RandomUtils.getRandomWord(5),
                RandomUtils.getRandomWord(5));
        return updateUser(id, updateForUser);
    }
}

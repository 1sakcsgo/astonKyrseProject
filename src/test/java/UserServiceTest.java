import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.homework.Exeption.EntityAlreadyExistsException;
import ru.aston.homework.Exeption.EntityNotFoundException;

import ru.aston.homework.Exeption.WrongPasswordException;
import ru.aston.homework.dao.UserDAOImp;
import ru.aston.homework.entity.User;
import ru.aston.homework.dto.UserForm;
import ru.aston.homework.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {

    UserService userService;


    @BeforeEach
    void setUp() {

        userService = new UserService(new UserDAOImp());
    }

    @Test
    void testgetUserByid() throws EntityNotFoundException {
        User expectedUser = new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "pass");
        User userFromService = userService.getUserById("efb7ac4e-4b94-42a1-bd00-e18631042d19");
        assertEquals(expectedUser, userFromService);

    }

    @Test
    void testloginUser() throws WrongPasswordException {
        User expectedUser = new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "pass");
        User userFromService = userService.login(expectedUser);
        assertEquals(expectedUser, userFromService);

    }

    @Test
    void testChangePass() throws WrongPasswordException, EntityAlreadyExistsException {
        User expectedUser = new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "newPass");
        User newUser = userService.changePass(new UserForm("name", "pass", "newPass"));
        assertEquals(expectedUser, newUser);

    }

    @Test
    void tesGetAllUser() throws EntityNotFoundException {
        List<User> initdata = Arrays
                .asList(new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "pass"),
                        new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e1d641042d19"), "na", "pass"),
                        new User(UUID.fromString("ffb7ac4e-4b94-42a1-bd00-e18641042d19"), "da", "pass"),
                        new User(UUID.fromString("bfb7ac4e-4b94-42a1-bd00-e18641042d19"), "ra", "pass"));

        assertEquals(initdata, userService.getAllUser());
    }
}

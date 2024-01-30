import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.homework.Exeption.EntityAlreadyExistsException;
import ru.aston.homework.Exeption.EntityNotFoundException;
import ru.aston.homework.Exeption.WrongPasswordException;
import ru.aston.homework.dao.UserRepositoryImp;
import ru.aston.homework.dto.UserForm;
import ru.aston.homework.entity.User;
import ru.aston.homework.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepositoryImp userRepositoryImp;

    @InjectMocks
    private UserService userService;


    @Test
    void testGetUserByid() throws EntityNotFoundException {
        User expectedUser = new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "pass");
        Mockito.when(userRepositoryImp.findById("efb7ac4e-4b94-42a1-bd00-e18631042d19")).thenReturn(expectedUser);
        User userFromService = userService.getUserById("efb7ac4e-4b94-42a1-bd00-e18631042d19");
        assertEquals(expectedUser, userFromService);

    }

    @Test
    void testGetUserByIdThrowExIfUserDoesNotExist() {
        Mockito.lenient().when(userRepositoryImp.findById("efb7ac4e-4b94-42a1-bd00-e18631042d13")).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById("efb7ac4e-4b94-42a1-bd00-e18631042d19"));
    }

    @Test
    void testGetUserByIdThrowExIfIdUncorrect() {
        Mockito.lenient().when(userRepositoryImp.findById("efb7ac4e-4b94-42a1-bd00-e1863104545342d13")).thenThrow(new IllegalArgumentException());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById("efb7ac4e-4b94-42a1-bd00-e18631042d19"));
    }

    @Test
    void testLoginUser() throws WrongPasswordException {
        User expectedUser = new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "pass");
        User formLogin = new User("name", "pass");
        Mockito.when(userRepositoryImp.findByUsername("name"))
                .thenReturn(new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "pass"));
        User userFromService = userService.login(formLogin);
        assertEquals(expectedUser, userFromService);

    }

    @Test
    void testSignUpUserDoesNotExist() throws EntityAlreadyExistsException {
        User expectedUser = new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "pass");
        Mockito.when(userRepositoryImp.isPresent("name")).thenReturn(false);
        User userSingUp = new User("name", "pass");
        User userSuccessSignUp = userService.signUp(userSingUp);
        userSuccessSignUp.setId(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"));
        assertEquals(expectedUser, userSuccessSignUp);

    }


    @Test
    void testChangePass() throws EntityAlreadyExistsException, EntityNotFoundException {
        String id = "efb7ac4e-4b94-42a1-bd00-e18631042d19";
        User expectedUser = new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "newPass");
        UserForm changeForm = new UserForm("pass", "newPass");
        Mockito.when(userRepositoryImp.findById(id)).thenReturn(expectedUser);
        Mockito.when(userService.getUserById(id))
                .thenReturn(new User(UUID.fromString(id), "name", "pass"));
        User fromDb = userService.getUserById(id);
        Mockito.when(userRepositoryImp.update(fromDb)).thenReturn(expectedUser);
        User newUser = userService.changePass(changeForm, id);
        assertEquals(expectedUser, newUser);

    }

    @Test
    void tesGetAllUser() throws EntityNotFoundException {
        List<User> initdata = Arrays
                .asList(new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "pass"),
                        new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e1d641042d19"), "na", "pass"),
                        new User(UUID.fromString("ffb7ac4e-4b94-42a1-bd00-e18641042d19"), "da", "pass"),
                        new User(UUID.fromString("bfb7ac4e-4b94-42a1-bd00-e18641042d19"), "ra", "pass"));
        Mockito.when(userRepositoryImp.index()).thenReturn(initdata);

        List<User> userListFromDb = userService.getAllUser();
        assertEquals(initdata, userListFromDb);
    }
}

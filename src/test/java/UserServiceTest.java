import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.homework.Exeption.EntityAlreadyExistsException;
import ru.aston.homework.Exeption.EntityNotFoundException;
import ru.aston.homework.Exeption.WrongPasswordException;
import ru.aston.homework.repository.UserRepo;
import ru.aston.homework.dto.UserForm;
import ru.aston.homework.entity.User;
import ru.aston.homework.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepositoryImp;

    @InjectMocks
    private UserService userService;


    @Test
    void testGetUserByid() throws EntityNotFoundException {
        User expectedUser = new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "pass");
        Mockito.when(userRepositoryImp.findById(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"))).thenReturn(Optional.of(expectedUser));
        User userFromService = userService.getUserById("efb7ac4e-4b94-42a1-bd00-e18631042d19");
        assertEquals(expectedUser, userFromService);

    }

    @Test
    void testGetUserByIdThrowExIfUserDoesNotExist() {
        Mockito.lenient().when(userRepositoryImp.findById(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d13"))).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById("efb7ac4e-4b94-42a1-bd00-e18631042d19"));
    }

    @Test
    void testGetUserByIdThrowExIfIdUncorrect() {

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
        Mockito.when(userRepositoryImp.existsByUsername("name")).thenReturn(false);
        User userSingUp = new User("name", "pass");
        User userSuccessSignUp = userService.signUp(userSingUp);
        userSuccessSignUp.setId(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"));
        assertEquals(expectedUser, userSuccessSignUp);

    }


    @Test
    void testChangePass() throws EntityAlreadyExistsException, EntityNotFoundException {
        String userId = "efb7ac4e-4b94-42a1-bd00-e18631042d19";

        UserForm userForm = new UserForm("pass", "newPass");

        User existingUser = new User(UUID.fromString(userId), "testUser", "oldPassword");

        Mockito.when(userRepositoryImp.findById(UUID.fromString(userId))).thenReturn(Optional.of(existingUser));

        User updatedUser = userService.changePass(userForm, userId);

        assertEquals(userForm.getNewPass(), updatedUser.getPassword());

    }

    @Test
    void changePass_PasswordAlreadyUsed() {
        // Arrange
        String userId = "123e4567-e89b-12d3-a456-426614174001";
        UserForm userForm = new UserForm("pass", "oldPassword");

        User existingUser = new User(UUID.fromString(userId), "testUser", "oldPassword");

        Mockito.when(userRepositoryImp.findById(UUID.fromString(userId))).thenReturn(Optional.of(existingUser));

        assertThrows(EntityAlreadyExistsException.class, () -> userService.changePass(userForm, userId));
    }


    @Test
    void tesGetAllUser() throws EntityNotFoundException {
        List<User> initdata = Arrays
                .asList(new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "pass"),
                        new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e1d641042d19"), "na", "pass"),
                        new User(UUID.fromString("ffb7ac4e-4b94-42a1-bd00-e18641042d19"), "da", "pass"),
                        new User(UUID.fromString("bfb7ac4e-4b94-42a1-bd00-e18641042d19"), "ra", "pass"));
        Mockito.when(userRepositoryImp.findAll()).thenReturn(initdata);

        List<User> userListFromDb = userService.getAllUser();
        assertEquals(initdata, userListFromDb);
    }
}

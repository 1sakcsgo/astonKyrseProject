package ru.aston.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.aston.homework.Exeption.UserExeption;
import ru.aston.homework.dao.UserDAOImp;
import ru.aston.homework.dao.UserRepo;
import ru.aston.homework.entity.User;
import ru.aston.homework.dto.UserForm;

import java.util.List;
import java.util.UUID;

/**
 * Класс для работы с пользователем
 */
@Service
public class UserService {

    /**
     * Поле репозитория
     */
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserDAOImp userDAOImp) {
        this.userRepo = userDAOImp;
    }

    /**
     * Получение пользователя по уникальному индитификатору
     * @param id индитификатор ползователя
     * @return обьект пользователя согласно ID
     */
    public User getUserById(String id) throws UserExeption {
        User userFromDb =userRepo.getUserById(id);
        if (userFromDb!=null){
            return userFromDb;
        }else {
            throw new UserExeption("user is not exist");
        }

    }

    /**
     * Сохраняет (регестрирует) пользователя в системе
     * @param user обьект пользователя для добавления
     * @return обьект пользователя при успешном добавлении
     * @throws UserExeption
     */
    public ResponseEntity<User> signUp(User user) throws UserExeption {
        if (!userRepo.isPresent(user.getUsername())) {
            user.setId(UUID.randomUUID());
            userRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new UserExeption("User exist");

        }

    }

    /**
     * Авторизирует пользователя
     * @param user обьект прользователя с данными для входа
     * @return обьект пользователя при успешной авторизаци
     * @throws UserExeption ошибка, если данные пользователя неверные
     */
    public ResponseEntity<User> login(User user) throws UserExeption {
        User userFromDb = userRepo.getUserByUsername(user.getUsername());
        if (userFromDb != null) {
            if (userFromDb.getPassword().equals(user.getPassword())) {
                return new ResponseEntity<>(userFromDb, HttpStatus.OK);
            }
        }
        throw new UserExeption("Uncorrect password or username");
    }

    /**
     * Меняет текуий пароль
     * @param userForm обьект с старым и новым паролем
     * @return возвращает измененный обьект
     * @throws UserExeption уведомляет об ошибке
     */
    public ResponseEntity<User> changePass(UserForm userForm) throws UserExeption {

        User userFromDb = login(new User(userForm.getUsername(), userForm.getPassword())).getBody();

        if (!userFromDb.getPassword().equals(userForm.getNewPass())) {

            userFromDb.setPassword(userForm.getNewPass());
            userFromDb = userRepo.update(userFromDb); //имитация работы с бд и без метода update пароль поменяется,
                                                        // так как userFromDb является ссылкой на обьект в списке
            return new ResponseEntity<>(userFromDb, HttpStatus.OK);
        }
        throw new UserExeption("Password already used");

    }

    /**
     * Возвращает всех пользователей
     * @return список пользователей
     */
    public List<User> getAllUser() {
        return userRepo.index();
    }


}

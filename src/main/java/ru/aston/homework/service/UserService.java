package ru.aston.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.aston.homework.Exeption.EntityAlreadyExistsException;
import ru.aston.homework.Exeption.EntityNotFoundException;
import ru.aston.homework.Exeption.WrongPasswordException;
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
     *
     * @param id индитификатор ползователя
     * @return обьект пользователя согласно ID
     */
    public User getUserById(String id) throws EntityNotFoundException {
        User userFromDb = null;
        try {
            userFromDb = userRepo.getUserById(id);
            if (userFromDb != null) {
                return userFromDb;
            } else {
                throw new EntityNotFoundException("user does not exist");
            }
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("user does not exist");
        }


    }

    /**
     * Сохраняет (регистрирует) пользователя в системе
     *
     * @param user обьект пользователя для добавления
     * @return обьект пользователя при успешном добавлении
     * @throws EntityAlreadyExistsException
     */
    public ResponseEntity<User> signUp(User user) throws EntityAlreadyExistsException {
        if (!userRepo.isPresent(user.getUsername())) {
            user.setId(UUID.randomUUID());
            userRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new EntityAlreadyExistsException("User exist");

        }

    }

    /**
     * Авторизирует пользователя
     *
     * @param user обьект прользователя с данными для входа
     * @return обьект пользователя при успешной авторизаци
     * @throws WrongPasswordException ошибка, если данные пользователя неверные
     */
    public ResponseEntity<User> login(User user) throws WrongPasswordException {
        User userFromDb = userRepo.getUserByUsername(user.getUsername());
        if (userFromDb != null) {
            if (userFromDb.getPassword().equals(user.getPassword())) {
                return new ResponseEntity<>(userFromDb, HttpStatus.OK);
            }
        }
        throw new WrongPasswordException("Uncorrected password or username");
    }

    /**
     * Меняет текуий пароль
     *
     * @param userForm обьект с старым и новым паролем
     * @return возвращает измененный обьект
     */
    public ResponseEntity<User> changePass(UserForm userForm) throws WrongPasswordException, EntityAlreadyExistsException {

        User userFromDb = login(new User(userForm.getUsername(), userForm.getPassword())).getBody();

        if (!userFromDb.getPassword().equals(userForm.getNewPass())) {

            userFromDb.setPassword(userForm.getNewPass());
            userFromDb = userRepo.update(userFromDb); //имитация работы с бд и без метода update пароль поменяется,
            // так как userFromDb является ссылкой на обьект в списке
            return new ResponseEntity<>(userFromDb, HttpStatus.OK);
        }
        throw new EntityAlreadyExistsException("Password already used");

    }

    /**
     * Возвращает всех пользователей
     *
     * @return список пользователей
     */
    public List<User> getAllUser() {
        return userRepo.index();
    }


}

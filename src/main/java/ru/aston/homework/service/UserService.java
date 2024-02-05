package ru.aston.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aston.homework.Exeption.EntityAlreadyExistsException;
import ru.aston.homework.Exeption.EntityNotFoundException;
import ru.aston.homework.Exeption.WrongPasswordException;
import ru.aston.homework.repository.UserRepo;
import ru.aston.homework.dto.UserForm;
import ru.aston.homework.entity.User;

import java.util.List;
import java.util.Optional;
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
    public UserService(UserRepo userDAOImp) {
        this.userRepo = userDAOImp;
    }

    /**
     * Получение пользователя по уникальному индитификатору
     *
     * @param id индитификатор ползователя
     * @return обьект пользователя согласно ID
     */
    public User getUserById(String id) throws EntityNotFoundException {

        try {
            return userRepo.findById(UUID.fromString(id))
                    .orElseThrow(()-> new EntityNotFoundException("user does not exist"));

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
    public User signUp(User user) throws EntityAlreadyExistsException {
        if (!userRepo.existsByUsername(user.getUsername())) {
            userRepo.save(user);
            return user;
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
    public User login(User user) throws WrongPasswordException {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null && userFromDb.getPassword().equals(user.getPassword())) {
            return userFromDb;
        }

        throw new WrongPasswordException("Incorrect password or username");
    }

    /**
     * Меняет текуий пароль
     *
     * @param userForm обьект с старым и новым паролем
     * @return возвращает измененный обьект
     */
    public User changePass(UserForm userForm, String id) throws EntityAlreadyExistsException, EntityNotFoundException {

        User userFromDb = getUserById(id);
        if (!userFromDb.getPassword().equals(userForm.getNewPass())) {
            userFromDb.setPassword(userForm.getNewPass());
            userRepo.save(userFromDb);
            return userFromDb;
        }
        throw new EntityAlreadyExistsException("Password already used");

    }

    /**
     * Возвращает всех пользователей
     *
     * @return список пользователей
     */
    public List<User> getAllUser() throws EntityNotFoundException {
        List<User> userList = userRepo.findAll();
        if (userList.isEmpty()) {
            throw new EntityNotFoundException("user does not exist");
        } else {
            return userList;
        }

    }


}

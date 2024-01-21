package ru.aston.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.aston.homework.Exeption.UserExeption;
import ru.aston.homework.dao.UserDAO;
import ru.aston.homework.dto.User;
import ru.aston.homework.dto.UserForm;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {


    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUserById(String id) {
        return userDAO.getUserById(id);
    }

    public ResponseEntity<User> saveUser(User user) throws UserExeption {
        if (!userDAO.isPresent(user.getUsername())) {
            user.setId(UUID.randomUUID());
            userDAO.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new UserExeption("User exist");

        }

    }

    public ResponseEntity<User> login(User user) throws UserExeption {
        User userFromDb = userDAO.getUserByUsername(user.getUsername());
        if (userFromDb != null) {
            if (userFromDb.getPassword().equals(user.getPassword())) {
                return new ResponseEntity<>(userFromDb, HttpStatus.OK);
            }
        }
        throw new UserExeption("Uncorrect password or username");
    }

    public ResponseEntity<User> changePass(UserForm userForm) throws UserExeption {

        User userFromDb = login(new User(userForm.getUsername(), userForm.getPassword())).getBody();

        if (!userFromDb.getPassword().equals(userForm.getNewPass())) {

            userFromDb.setPassword(userForm.getNewPass());
            userFromDb = userDAO.update(userFromDb);
            return new ResponseEntity<>(userFromDb, HttpStatus.OK);
        }
        throw new UserExeption("Password already used");

    }


    public List<User> getAllUser() {
        return userDAO.index();
    }


}

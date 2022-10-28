package pl.put.backendoctodisco.service;

import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.exceptions.UserEmailAlreadyExistsException;
import pl.put.backendoctodisco.exceptions.UserLoginAlreadyExistsException;

public interface UserService {
    User createUser(User user) throws UserLoginAlreadyExistsException, UserEmailAlreadyExistsException;

    User findByLogin(User user);
}

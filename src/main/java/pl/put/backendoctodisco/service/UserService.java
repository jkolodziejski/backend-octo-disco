package pl.put.backendoctodisco.service;

import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.User;

public interface UserService {
    User createUser(User user);

    User findByLogin(User user);
}

package pl.put.backendoctodisco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.exceptions.TokenNotFoundException;
import pl.put.backendoctodisco.exceptions.UserEmailAlreadyExistsException;
import pl.put.backendoctodisco.exceptions.UserLoginAlreadyExistsException;
import pl.put.backendoctodisco.repository.UserRepository;
import pl.put.backendoctodisco.utils.AuthToken;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) throws UserLoginAlreadyExistsException, UserEmailAlreadyExistsException {
        List<User> usersByLogin = userRepository.findByLogin(user.getLogin());
        List<User> usersByEmail = userRepository.findByEmail(user.getEmail());
        if(!usersByLogin.isEmpty()){
            throw new UserLoginAlreadyExistsException();
        }
        if(!usersByEmail.isEmpty()){
            throw new UserEmailAlreadyExistsException();
        }
        return userRepository.save(user);
    }

    public Optional<User> findByLogin(String login) {
        List<User> users = userRepository.findByLogin(login);
        return users.stream().findFirst();
    }

    public AuthToken authorizeUser(User user) {
        AuthToken token = new AuthToken(user);
        userRepository.setUserInfoById(token.toString(), user.getId());
        return token;
    }

    public User findUserByAuthToken(String authToken) throws TokenNotFoundException {
        Optional<User> userToCheck = userRepository.findByAuthToken(authToken).stream().findFirst();
        if(userToCheck.isEmpty()){
            throw new TokenNotFoundException();
        }

        return userToCheck.get();
    }
}

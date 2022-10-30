package pl.put.backendoctodisco.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.exceptions.UserEmailAlreadyExistsException;
import pl.put.backendoctodisco.exceptions.UserLoginAlreadyExistsException;
import pl.put.backendoctodisco.repository.UserRepository;
import pl.put.backendoctodisco.service.UserService;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
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

    @Override
    public User findByLogin(User user) {
        List<User> users = userRepository.findByLogin(user.getLogin());
        if(users.isEmpty()){
            return null;
        }
        return users.stream().findFirst().get();
    }
}

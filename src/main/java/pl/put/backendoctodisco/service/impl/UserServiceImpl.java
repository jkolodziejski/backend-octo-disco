package pl.put.backendoctodisco.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.repository.UserRepository;
import pl.put.backendoctodisco.service.UserService;

import java.util.List;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean findByLogin(User user) {
        List<User> users = userRepository.findByLogin(user.getLogin());
        if(users.stream().findFirst().isPresent()){
            return Objects.equals(users.stream().findFirst().get().getPassword(), user.getPassword());
        }
        return false;
    }
}

package pl.put.backendoctodisco.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.put.backendoctodisco.entity.User;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.login = ?1")
    List<User> findByLogin(String name);

    List<User> findByEmail(String email);

    List<User> findByAuthToken(String authToken);

    @Modifying
    @Query("update User u set u.authToken = ?1 where u.id = ?2")
    void setUserInfoById(String authToken, Long id);

}
